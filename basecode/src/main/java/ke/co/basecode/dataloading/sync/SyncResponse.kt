package ke.co.basecode.dataloading.sync

data class SyncResponse<T>(val timestamp: String,
                           val new: List<T>,
                           val updated: List<T>,
                           val deleted: List<T>)