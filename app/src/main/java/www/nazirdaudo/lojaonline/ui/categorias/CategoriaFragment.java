package www.nazirdaudo.lojaonline.ui.categorias;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import www.nazirdaudo.lojaonline.R;
import www.nazirdaudo.lojaonline.databinding.FragmentCategoriaBinding;

public class CategoriaFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_categoria, container, false);

        return root;
    }

}