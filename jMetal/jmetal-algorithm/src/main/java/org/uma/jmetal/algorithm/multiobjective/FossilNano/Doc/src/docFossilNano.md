# FossilNano

## Środowisko
Pierwszym krokiem jest instalacja oprogramowania do wirtualizacji VMware. Następnie należy ściągnąć obraz systemu. W tym przypadku był to Linux Ubuntu 16.04. Po przejściu przez interfejs do dodawania maszyny wirtualnej ukaże się ekran logowania systemu. Na starcie należy doinstalować szereg pakietów, niezbędnych do działania symulacji. Po uruchomieniu terminala:

```sh
$ cd
$ sudo apt install git
$ sudo apt install cmake
$ sudo apt install emacs24
$ sudo apt install gfortran
$ sudo apt install liblapack-doc
$ sudo apt install liblapack-dev
$ sudo apt install libblas-doc
$ sudo apt install libblas-dev
$ sudo apt install libboost-all-dev
$ sudo apt install gnuplot
```

W następnej kolejności należy zainstalować Galois. Po ściągnięciu instalatora z oficjalnej strony:

```sh
$ cd Downloads/
$ mv Galois-2.2.1.tar.gz ~
$ cd ..
$ tar xzvf Galois-2.2.1.tar.gz
$ cd Galois-2.2.1/build
$ mkdir release
$ cd release/
$ cmake -DSKIP_COMPILE_APPS=ON ../..
$ make
$ sudo make install
```

Kolejny krok to zainstalowanie oprogramowania do testów jednostkowych:
```sh
$ cd Downloads/
$ mv libunittest-9.3.5.tar.gz ~
$ cd ..
$ tar xvf libunittest-9.3.5.tar.gz
$ cd libunittest-9.3.5/
$ ./configure
$ make
$ sudo make install
```
Teraz możemy zabrać się za instalację programu generującego złoże:
```sh
$ cd ~
$ git clone https://github.com/marcinlos/iga-ads
$ cd iga-ads/
$ rm CMakeCache.txt
$ cmake -DUSE_GALOIS=ON . && make
```

## Uruchamianie
Po wykonaniu wszystkich wcześniejszych kroków możemy podjąć się uruchomienia programu. Należy przejść do katalogu z plikami wykonywalnymi i uruchomić wybrany plik.
```sh
$ cd ~/iga-ads/
$ ./flow
```
W tym momencie zaczną generować się pliki wyjściowe z rozszerzeniem .vti. Aby zobaczyć efekty należy otworzyć wygenerowane pliki w programie ParaView. W tym celu warto przenieść wszystkie wygenerowane wyjścia do jednego katalogu. ParaView jest aplikacją do wizualizacji dużych zbiorów danych. Po otworzeniu zbioru danych i wybraniu opcji Display > Representation > Volume ukaże się nam widok podobny do poniższego:

\begin{figure}[H]
    \centering
    \includegraphics[width=\textwidth]{./figures/zloze1}
    \caption{Przykładowe złoże obserwowane w 3D}
    \label{fig:zloze1}
\end{figure}

Możemy także zobaczyć przekrój zaznaczając opcję Slice. W tym widoku można zauważyć co dzieje się wewnątrz złoża. Przykładowy widok:

\begin{figure}[H]
    \centering
    \includegraphics[width=\textwidth]{./figures/zloze2}
    \caption{Przekrój złoża}
    \label{fig:zloze2}
\end{figure}

W górnej części programu możemy także uruchomić symulację, która generuje się z plików wyjściowych.