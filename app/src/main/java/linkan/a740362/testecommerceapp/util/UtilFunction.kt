package linkan.a740362.testecommerceapp.util

import android.util.SparseArray
import android.util.SparseIntArray
import org.json.JSONArray
import org.json.JSONObject

object UtilFunction {

    fun segregateProductCategory(originalJson: String): String {


        val json = JSONObject(originalJson)
        val category: JSONArray = if (json.has("categories")) json.getJSONArray("categories") else JSONArray()
        val rankings: JSONArray = if (json.has("rankings")) json.getJSONArray("rankings") else JSONArray()


        /*    recursiveSubcategory(
               category,
               category.getJSONObject(0).getJSONArray("child_categories"),
               0,
               0,
               0,
               0
           )

            outer@ while (parentCount < category.length()) {

                 if (category.getJSONObject(parentCount).has("child_categories")
                     && category.getJSONObject(parentCount).getJSONArray("child_categories").length() > 0
                 ) {
                     var childCount = 0

                     inner@ while (childCount < category.getJSONObject(parentCount).getJSONArray("child_categories").length()) {

                     }

                 } else
                     parentCount++

             }*/

        return filterProductByCategory(category, rankings).toString()
    }


    fun recursiveSubcategory(
        category: JSONArray,
        child_category: JSONArray,
        parentCount: Int,
        childCount1: Int,
        childCount2: Int,
        childId: Int
    ) {

        if (child_category.length() > 0 || childCount2 < category.length()) {


            if (childCount1 < child_category.length() && child_category.length() > 0) {

                if (childCount2 < category.length() && category.length() > 0) {

                    if (childId == category.getJSONObject(childCount2).getInt("id")) {

                        val subCategory1 = category.getJSONObject(childCount2)

                        // remove it from original category
                        category.remove(childCount2)
                        // add to the parent category as a child subcategory
                        child_category.put(childCount1, subCategory1)

                        if (subCategory1.getJSONArray("child_categories").length() > 0) {

                        }

                    } else
                        recursiveSubcategory(
                            category,
                            child_category,
                            parentCount,
                            childCount1,
                            childCount2 + 1,
                            child_category.getInt(childCount1)
                        )


                } else
                    recursiveSubcategory(
                        category,
                        child_category,
                        parentCount,
                        childCount1 + 1,
                        0,
                        child_category.getInt(childCount1 + 1)
                    )

            } else
                recursiveSubcategory(
                    category,
                    category.getJSONObject(parentCount + 1).getJSONArray("child_categories"),
                    parentCount + 1,
                    childCount1,
                    childCount2,
                    childId
                )



            outer@ while (childCount1 < child_category.length()) {

                if (child_category.get(childCount1) is Int) {

                    val childId = child_category.getInt(childCount1)

                    inner@ while (childCount2 < category.length()) {

                        if (childId == category.getJSONObject(childCount2).getInt("id")) {

                            val subCategory = category.remove(childCount2)

                            child_category.put(childCount1, subCategory)

                        }
                    }

                    recursiveSubcategory(
                        category,
                        child_category,
                        parentCount,
                        0,
                        0,
                        child_category.getInt(childCount1)
                    )
                }

            }

        } else {

            recursiveSubcategory(
                category,
                category.getJSONObject(parentCount + 1).getJSONArray("child_categories"),
                parentCount + 1,
                0,
                0,
                0
            )

        }

    }


    //-----------------------------------------------------

    fun filterProductByCategory(category: JSONArray, rankings: JSONArray): JSONArray {

        val response = JSONArray()

        val categoryMap = SparseArray<JSONObject>()
        val subCategoryMap = SparseIntArray()


        for (i in 0 until category.length()) {

            val catObject = category.getJSONObject(i)

            for (j in 0 until catObject.getJSONArray("child_categories").length()) {

                //  if (catObject.getJSONArray("child_categories").length() > 0)
                val subCategoryId = catObject.getJSONArray("child_categories").getInt(j)

                subCategoryMap.put(subCategoryId, catObject.getInt("id"))
            }
        }


        for (i in 0 until category.length()) {

            val catObject = category.getJSONObject(i)

            for (k in 0 until catObject.getJSONArray("child_categories").length()) {

                catObject.getJSONArray("child_categories").remove(0) // remove all child category index

            }

            categoryMap.put(catObject.getInt("id"), catObject)
        }


        for (i in 0 until category.length()) {

            val catObject = category.getJSONObject(i)

            if (subCategoryMap.get(catObject.getInt("id"), 0) != 0) {

                val subCategoryId = catObject.getInt("id")   // key for sub category

                val categoryId = subCategoryMap.get(catObject.getInt("id")) // value for subcategory

                /*    val subCategoryObj = category.getJSONObject(subCategoryId) // accessed the subcategory based on position

                    category.remove(subCategoryId) // remove category json object

                    category.put(
                        categoryId,
                        category
                            .getJSONObject(categoryId)
                            .getJSONArray("child_categories")
                            .put(categoryId, subCategoryObj)
                    )*/

                val subCategoryObj = categoryMap.get(subCategoryId) // accessed the subcategory based on position

                categoryMap.remove(subCategoryId) // remove category json object


                val jsonObject = categoryMap.get(categoryId)  // extract json object

                /*  for (k in 0 until jsonObject.getJSONArray("child_categories").length()) {
                      if (jsonObject.getJSONArray("child_categories").getInt(k) == subCategoryId) {

                          jsonObject.getJSONArray("child_categories").remove()

                      }
                  }*/

                jsonObject.getJSONArray("child_categories")  // set json aaray
                    .put(subCategoryObj)


                categoryMap.put(categoryId, jsonObject) // update map

            }

        }

        for (i in 0 until category.length()) {
            val obj = categoryMap.get(i, JSONObject())
            if (obj.length() > 0)
                response.put(categoryMap.get(i, obj))
        }

        return response

    }

}