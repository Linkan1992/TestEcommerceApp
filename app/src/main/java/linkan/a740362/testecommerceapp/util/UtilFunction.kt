package linkan.a740362.testecommerceapp.util

import android.util.SparseArray
import android.util.SparseIntArray
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception

object UtilFunction {

    fun segregateProductCategory(originalJson: String): String {


        val json = JSONObject(originalJson)
        val category: JSONArray = if (json.has("categories")) json.getJSONArray("categories") else JSONArray()
        val rankings: JSONArray = if (json.has("rankings")) json.getJSONArray("rankings") else JSONArray()


        return filterProductByCategory(category, rankings).toString()
    }


    //-----------------------------------------------------

    fun filterProductByCategory(category: JSONArray, rankings: JSONArray): JSONObject {

        try {

            val response = JSONArray()
            val responseObj = JSONObject()

            val categoryMap = SparseArray<JSONObject>()
            val subCategoryMap = SparseIntArray()

            val flatProductMap = SparseArray<JSONObject>()


            /**
             * Creating subcategory map with "subcategoryId" as key and "categoryId" as value
             * Creating a product map by flattening the category response with "productId" as key as "productObj" as value
             */
            for (i in 0 until category.length()) {

                val catObject = category.getJSONObject(i)

                for (j in 0 until catObject.getJSONArray("child_categories").length()) {

                    val subCategoryId = catObject.getJSONArray("child_categories").getInt(j)

                    subCategoryMap.put(subCategoryId, catObject.getInt("id"))
                }

                // creating  products flat map
                for (k in 0 until catObject.getJSONArray("products").length()) {

                    val productObj = catObject.getJSONArray("products").getJSONObject(k)

                    flatProductMap.put(
                        productObj.getInt("id"),
                        productObj
                    )

                }
            }


            /**
             * Creating category map with "categoryId" as key and "categoryObj" as value by deleting all the
             * child_categories elements
             */
            for (i in 0 until category.length()) {

                val catObject = category.getJSONObject(i)

                for (k in 0 until catObject.getJSONArray("child_categories").length()) {

                    catObject.getJSONArray("child_categories").remove(0) // remove all child category index

                }

                categoryMap.put(catObject.getInt("id"), catObject)
            }


            /**
             * Rebuilding the category map based on actual parent and child category object relation
             * also generating child_categories array based on nested child tree
             */
            for (i in 0 until category.length()) {

                val catObject = category.getJSONObject(i)

                if (subCategoryMap.get(catObject.getInt("id"), 0) != 0) {

                    val subCategoryId = catObject.getInt("id")   // key for sub category

                    val categoryId = subCategoryMap.get(catObject.getInt("id")) // value for subcategory


                    val subCategoryObj = categoryMap.get(subCategoryId) // accessed the subcategory based on position

                    categoryMap.remove(subCategoryId) // remove category json object


                    val jsonObject = categoryMap.get(categoryId)  // extract json object



                    jsonObject.getJSONArray("child_categories")  // set json aaray
                        .put(subCategoryObj)


                    categoryMap.put(categoryId, jsonObject) // update map

                }

            }

            /**
             * code to short product based on rank
             */

            for (i in 0 until rankings.length()) {

                val productArray = rankings.getJSONObject(i).getJSONArray("products")

                for (j in 0 until productArray.length()) {

                    var key = "view_count"
                    var valueCount = 0L

                    val productObj = productArray.getJSONObject(j)

                    val mapProductObj = flatProductMap.get(productObj.getInt("id"))


                    if (productObj.has("view_count")) {
                        key = "view_count"
                    } else if (productObj.has("order_count")) {
                        key = "order_count"
                    } else if (productObj.has("shares")) {
                        key = "shares"
                    }

                    valueCount = productObj.getLong(key)

                    // changing map product object
                    mapProductObj.put("view_count", valueCount)

                    // remove old product object from product array
                    //  productArray.remove(j)

                    // adding updated product array in product array
                    productArray.put(j, mapProductObj)
                }

            }

            /**
             * creating actual category json array response
             */
            for (i in 0 until category.length()) {
                val obj = categoryMap.get(i, JSONObject())
                if (obj.length() > 0)
                    response.put(categoryMap.get(i, obj))
            }

            responseObj.put("categories", response)
            responseObj.put("rankings", rankings)

            return responseObj

        } catch (e: Exception) {
            return JSONObject()
        }

    }

}