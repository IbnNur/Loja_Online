package www.nazirdaudo.lojaonline.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import www.nazirdaudo.lojaonline.Modelo.PopularModel;
import www.nazirdaudo.lojaonline.R;
import www.nazirdaudo.lojaonline.adapters.PopularAdapters;
import www.nazirdaudo.lojaonline.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    RecyclerView popularRec;
    List<PopularModel> popularModelList;
    PopularAdapters popularAdapters;
    FirebaseFirestore db;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        db = FirebaseFirestore.getInstance();

        popularRec = root.findViewById(R.id.pop_rec);

        popularRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,
                false));

        popularModelList = new ArrayList<>();
        popularAdapters = new PopularAdapters(getActivity(),popularModelList);
        popularRec.setAdapter(popularAdapters);

        db.collection("ProdutosPopulares")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful())
                        {
                            for (QueryDocumentSnapshot document : task.getResult())
                            {
                               PopularModel popularModel = document.toObject(PopularModel.class);
                               popularModelList.add(popularModel);
                               popularAdapters.notifyDataSetChanged();

                            }
                        }
                        else
                            {
                                Toast.makeText(getActivity(), "Erro"+task.getException(),
                                        Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        return root;
    }

}