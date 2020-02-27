package ke.co.basecode.network

/**
 * Created by Willy on 27/02/2020
 * Email williammakau070@gmail.com
 * @author willi
 */
data class Paginator(
    val totalCount: Int,
    val perPage: Int,
    val currentPage: Int,
    val lastPage: Int,
    val totalPages: Int,
    val from: Int,
    val to: Int
)