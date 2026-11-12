package com.example.books;                              // package da app

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;  // 9.1 itens em grelha

import com.example.books.adaptadores.GrelhaRecyclerAdaptador;
import com.example.books.adaptadores.OnLivroClickListener;
import com.example.books.databinding.FragmentGrelhaLivrosBinding;
import com.example.books.modelo.Livro;
import com.example.books.modelo.SingletonGestorLivros;

import java.util.ArrayList;

public class GrelhaLivrosFragment extends Fragment implements OnLivroClickListener {

    private static final int NUM_COLUNAS = 2;           // nº de colunas da grelha

    private FragmentGrelhaLivrosBinding binding;

    public GrelhaLivrosFragment() {
        // Construtor vazio obrigatório
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGrelhaLivrosBinding.inflate(inflater, container, false);

        ArrayList<Livro> livros = SingletonGestorLivros.getInstance().getLivros();
        GrelhaRecyclerAdaptador adaptador = new GrelhaRecyclerAdaptador(livros, this);

        // 9.1 GridLayoutManager em vez de LinearLayoutManager: 2 capas por linha
        binding.rvGrelha.setLayoutManager(new GridLayoutManager(requireContext(), NUM_COLUNAS));
        binding.rvGrelha.setAdapter(adaptador);

        return binding.getRoot();
    }

    @Override
    public void onLivroClick(Livro livro) {             // igual à lista: abre os detalhes
        Intent intent = new Intent(requireContext(), DetalhesLivroActivity.class);
        intent.putExtra(DetalhesLivroActivity.ID_LIVRO, livro.getId());
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}