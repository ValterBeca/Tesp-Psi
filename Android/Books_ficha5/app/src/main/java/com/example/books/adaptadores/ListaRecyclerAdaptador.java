package com.example.books.adaptadores;

import android.view.LayoutInflater;                     // cria as Views a partir do XML
import android.view.View;                               // parâmetro do onClick
import android.view.ViewGroup;                          // a RecyclerView (pai das linhas)

import androidx.annotation.NonNull;                     // parâmetro/retorno nunca é null
import androidx.recyclerview.widget.RecyclerView;       // RecyclerView.Adapter e RecyclerView.ViewHolder

import com.example.books.databinding.ItemListaLivroBinding; // gerada a partir de item_lista_livro.xml
import com.example.books.modelo.Livro;                  // classe Livro

import java.util.ArrayList;                             // lista de livros

// 5.1 O adaptador herda de RecyclerView.Adapter e diz qual é o seu ViewHolder
public class ListaRecyclerAdaptador extends RecyclerView.Adapter<ListaRecyclerAdaptador.ItemViewHolder> {

    private final ArrayList<Livro> listaLivros;         // 5.4 livros a mostrar
    private final OnLivroClickListener listener;        // 5.7.2 quem é avisado do clique (o fragmento)

    // 5.4 + 5.7.3 Construtor: recebe a lista de livros e o listener
    public ListaRecyclerAdaptador(ArrayList<Livro> listaLivros, OnLivroClickListener listener) {
        this.listaLivros = listaLivros;
        this.listener = listener;
    }

    // 5.5 Cria VISUALMENTE uma linha (só a estrutura, ainda sem dados)
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemListaLivroBinding binding =
                ItemListaLivroBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        // parent.getContext(): o adaptador não é uma atividade, por isso vai buscar o contexto à RecyclerView
        return new ItemViewHolder(binding);             // embrulha a linha num ViewHolder
    }

    // 5.6.1 Preenche a linha que está na posição "position" com os dados de um livro
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Livro livro = listaLivros.get(position);        // livro desta posição
        holder.bind(livro);                             // põe os dados nas Views da linha
    }

    // 5.3 Quantas linhas a RecyclerView tem de mostrar
    @Override
    public int getItemCount() {
        return listaLivros.size();                      // nº de livros
    }

    // 5.2 Classe INTERNA: guarda as Views de UMA linha para serem reaproveitadas no scroll
    public class ItemViewHolder extends RecyclerView.ViewHolder {
        // sem "static": assim consegue usar o "listener" da classe de fora

        private final ItemListaLivroBinding binding;    // 5.2.3 Views da linha

        // 5.2.3 Construtor: recebe o binding da linha
        public ItemViewHolder(ItemListaLivroBinding binding) {
            super(binding.getRoot());                   // o ViewHolder precisa da View raiz da linha
            this.binding = binding;
        }

        // 5.6.2 Associa os dados de um livro às Views da linha
        public void bind(Livro livro) {
            binding.ivCapa.setImageResource(livro.getCapa());      // capa
            binding.tvTitulo.setText(livro.getTitulo());           // título
            binding.tvSerie.setText(livro.getSerie());             // série
            binding.tvAutor.setText(livro.getAutor());             // autor
            binding.tvAno.setText(String.valueOf(livro.getAno())); // ano: int → String

            // 5.7.4 Clique na linha inteira: avisa o listener (o fragmento) com o livro desta linha
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onLivroClick(livro);
                }
            });
        }
    }
}