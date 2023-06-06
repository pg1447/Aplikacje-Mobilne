package pl.app.citiesonmap;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.firestore.FirebaseFirestore;

import pl.app.citiesonmap.data.City;
import pl.app.citiesonmap.databinding.FragmentAddCityBinding;

public class AddCityFragment extends BottomSheetDialogFragment {

    private FragmentAddCityBinding binding;

    public static AddCityFragment newInstance() {
        return new AddCityFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentAddCityBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.add.setOnClickListener(view1 -> addCity(binding.name.getText().toString(),
                binding.lat.getText().toString(),
                binding.lng.getText().toString()));

    }

    private void addCity(String name, String latString, String lngString) {
        if (name.isEmpty() || latString.isEmpty() || lngString.isEmpty()) {
            Toast.makeText(getContext(), "Fill data!", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseFirestore.getInstance()
                .collection("cities")
                .add(new City(name, Double.parseDouble(latString), Double.parseDouble(lngString)))
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getContext(), "City added to db", Toast.LENGTH_SHORT).show();
                        dismiss();
                    } else {
                        Toast.makeText(getContext(), "Error occured: " + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }
}
