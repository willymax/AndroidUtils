package ke.co.basecode.dataloading.adapter;

import androidx.annotation.NonNull;

public interface Updatable<T> {

  boolean areContentsTheSame(@NonNull T newItem);

  Object getChangePayload(@NonNull T newItem);

}