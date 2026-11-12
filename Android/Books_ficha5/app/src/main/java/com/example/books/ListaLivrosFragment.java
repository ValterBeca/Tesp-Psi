package com.example.books;                              // package da app

import android.content.Intent;                          // 8.2 abrir a DetalhesLivroActivity
import android.os.Bundle;                               // estado do fragmento
import android.view.LayoutInflater;                     // "insufla" o XML
import android.view.View;                               // View devolvida pelo onCreateView
import android.view.ViewGroup;                          // contentor do fragmento
import android.widget.Toast;                            // 6.4 Toast com o título

import androidx.fragment.app.Fragment;                  // classe base dos fragmentos
import androidx.recyclerview.widget.LinearLayoutManager; // 6.2 linhas umas por baixo das outras

import com.example.books.adaptadores.ListaRecyclerAdaptador; // adaptador da lista
import com.example.books.adaptadores.OnLivroClickListener;   // interface do clique
import com.example.books.databinding.FragmentListaLivrosBinding; // gerada a partir de fragment_lista_livros.xml
import com.example.books.modelo.Livro;                  // classe Livro
import com.example.books.modelo.SingletonGestorLivros;  // Singleton

import java.util.ArrayList;                             // lista de livros

// 6.3 "implements OnLivroClickListener": o fragmento é o listener dos cliques
public class ListaLivrosFragment extends Fragment implements OnLivroClickListener {

    private FragmentListaLivrosBinding binding;         // acesso às Views sem findViewById

    public ListaLivrosFragment() {
        // Construtor vazio obrigatório
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentListaLivrosBinding.inflate(inflater, container, false); // cria o binding

        ArrayList<Livro> livros = SingletonGestorLivros.getInstance().getLivros(); // os 10 livros
        ListaRecyclerAdaptador adaptador = new ListaRecyclerAdaptador(livros, this); // 6.1 "this" = este fragmento é o listener

        // 6.2 Sem LayoutManager a lista NÃO aparece
        binding.rvLivros.setLayoutManager(new LinearLayoutManager(requireContext())); // lista vertical
        binding.rvLivros.setAdapter(adaptador);         // liga o adaptador à RecyclerView

        return binding.getRoot();
    }

    // 6.3 Chamado pelo adaptador quando se clica num livro
    @Override
    public void onLivroClick(Livro livro) {
        // 6.4 Primeiro a ficha pede só um Toast com o título:
        Toast.makeText(requireContext(), livro.getTitulo(), Toast.LENGTH_SHORT).show();

        // 8.2 Depois (passo 8) abre os detalhes. Escreve estas 3 linhas só quando fizeres o passo 8:
        Intent intent = new Intent(requireContext(), DetalhesLivroActivity.class); // Intent explícito
        intent.putExtra(DetalhesLivroActivity.ID_LIVRO, livro.getId()); // 8.2.2 passa só o ID
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;                                 // liberta a memória (regra da Google para binding em fragmentos)
    }
}