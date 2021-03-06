package linkan.a740362.testecommerceapp.util

import android.annotation.SuppressLint
import android.util.SparseArray
import android.util.SparseIntArray
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception

object UtilFunction {

    private val colorMap = HashMap<String, String>()

    fun segregateProductCategory(originalJson: String): String {


        val json = JSONObject(originalJson)
        val category: JSONArray = if (json.has("categories")) json.getJSONArray("categories") else JSONArray()
        val rankings: JSONArray = if (json.has("rankings")) json.getJSONArray("rankings") else JSONArray()


        return filterProductByCategory(category, rankings).toString()
    }


    //-----------------------------------------------------

    private fun filterProductByCategory(category: JSONArray, rankings: JSONArray): JSONObject {

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

                    var key = ""
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

                    if (key.isNotEmpty()) {
                        valueCount = productObj.getLong(key)
                        // changing map product object
                        mapProductObj.put(key, valueCount)
                    }

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


    fun filterProductCategory(originalJson: String): String {


        val json = JSONObject(originalJson)
        val category: JSONArray = if (json.has("categories")) json.getJSONArray("categories") else JSONArray()
        val rankings: JSONArray = if (json.has("rankings")) json.getJSONArray("rankings") else JSONArray()


        return filterProductVariant(category, rankings).toString()
    }


    @SuppressLint("UseSparseArrays")
    private fun filterProductVariant(category: JSONArray, rankings: JSONArray): JSONObject {

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

                    val flatProductVariantMap = HashMap<Any?, JSONObject>()

                    val productObj = catObject.getJSONArray("products").getJSONObject(k)

                    val productVariant = productObj.getJSONArray("variants")

                    // filter product variant from product object
                    for (l in 0 until productVariant.length()) {

                        val variantObj = productVariant.getJSONObject(0)
                        val size = variantObj.get("size")
                        //  val size = variantObj.getInt("size") as Int?

                        val jsonObj = flatProductVariantMap.get(size)

                        if (jsonObj != null) {
                            variantObj.remove("size")
                            jsonObj
                                .getJSONArray("color_variant")
                                .put(variantObj)

                            flatProductVariantMap.put(size, jsonObj)
                            productVariant.remove(0)
                        } else {
                            val obj = JSONObject()
                            obj.put("size", size)
                            variantObj.remove("size")
                            val mapArray = JSONArray()
                            mapArray.put(variantObj)
                            obj.put("color_variant", mapArray)
                            flatProductVariantMap.put(size, obj)
                            productVariant.remove(0)
                        }

                    }

                    // for forming new Variant array
                    for (element in flatProductVariantMap.values) {
                        productVariant.put(element)
                    }


                    // --------------- adding object into flat product map ----------------

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

                    var key = ""
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

                    if (key.isNotEmpty()) {
                        valueCount = productObj.getLong(key)
                        // changing map product object
                        mapProductObj.put(key, valueCount)
                    }

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


    fun createColorMap() {

        colorMap.apply {
            this.put("Blue", "#0000FF")
            this.put("Red", "#DC143C")
            this.put("White", "#ffffff")
            this.put("Black", "#000000")
            this.put("Yellow", "#FFFF00")
            this.put("Green", "#008000")
            this.put("Brown", "#A52A2A")
            this.put("Silver", "#C0C0C0")
            this.put("Golden", "#D4AF37")
            this.put("Grey", "#808080")
            this.put("Light Blue", "#ADD8E6")
        }

    }

    fun getHexaDeciColor(key: String?): String {
        return colorMap.get(key) ?: "#ffffff"
    }

}