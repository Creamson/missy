\renewcommand{\figurename}{Rysunek}
\renewcommand\refname{Bibliografia}
\renewcommand\tablename{Tabela}

# Zastosowanie polioptymalizacji

Polioptymalizacja to proces optymalizacji, który ma na celu optymalizację więcej niż jednego parametru rozwiązania jednocześnie. Metoda ta może zostać zastosowana, gdy szukane rozwiązanie jest opisane przez różne zmienne, które składają się na jego jakość. W takim przypadku optymalizacja zakłada uwzględnianie korelacji między tymi składowymi parametrami.

W porównaniu do optymalizacji pojedynczego parametru polioptymalizacja może uzyskać lepsze wyniki, gdy natura problemu składa się z wielu wyznaczników jakości rozwiązania. Ze względu na jednoczesną optymalizację wszystkich wyznaczników, dąży do uzyskania jak najlepszej proporcji ich wartości.

Przykładem problemu polioptymalizacji jest wybór jednego produktu w oparciu o jego cenę oraz jakość (rysunek \ref{fig:intro_polioptimization}).

\begin{figure}[H]
    \centering
    \includegraphics{./resources/intro_polioptimization}
    \caption{Koncepcja polioptymalizacji}
    \label{fig:intro_polioptimization}
\end{figure}

Podczas wyboru rozwiązania chcemy, aby wybrany produkt miał jak najwyższą jakość oraz jednocześnie był jak najtańszy. Nie wybierzemy jednak produktu o najniższej cenie, ponieważ jego jakość będzie prawdopodobnie niska.
Podobnie podczas polioptymalizacji, należy ustalić kryterium wyboru rozwiązania posiadającego najkorzystniejszą korelację. W rozwiązaniu nie należy zawierać rozwiązania, które jest zdominowane (istnieje rozwiązanie, które jest lepsze od niego przynajmniej pod jednym względem i nie jest gorsze pod resztą względów).
Rozwiązaniem problemu polioptymalizacji jest tzw. front, czyli zbiór wszystkich rozwiązań niezdominowanych. Spośród tych rozwiązań należy wybrać rozwiązanie empirycznie lub dla którego korelacja jego cech jest najkorzystniejsza według zadanych kryteriów.

## Polioptymalizacja modelu sieci neuronowej

**Dziedzina**

Na podstawie publikacji \cite{intro_neuro} można stwierdzić, że dobry model sieci neuronowej powinien być jak najmniej skomplikowany (niska liczba połączeń w sieci) przy jak najmniejszym średnim błędzie aproksymacji dla zbioru testowego (wysoka skuteczność klasyfikacji danych niezobaczonych wcześniej). Wynika to z faktu, że zbyt złożona struktura sieci zawierająca wiele połączeń może doprowadzić do nadmiernego dopasowania sieci do danych treningowych. Nadmierne dopasowanie może obniżyć skuteczność sieci neuronowej w stosunku do danych niezobaczonych wcześniej. Z drugiej strony, modelu sieci neuronowej o możliwie niskiej złożoności może pozwolić na wydobycie z niej zależności w badanym zbiorze danych. Należy jednak uważać, ponieważ zbyt niska złożoność sieci może nie pozwolić na odwzorowanie zależności występujących w zbiorze danych.

**Opis problemu**

Złożoność modelu sieci neuronowej może być wyznaczona empirycznie, na podstawie badań nad konkretnym zbiorem danych. W publikacji \cite{intro_neuro} problem znalezienia modelu sieci neuronowej został jednak sprowadzony do problemu polioptymalizacji tego modelu ze względu na dwa parametry:
\begin{itemize}
	\item $MSE$ (ang. Mean Squared Error) - błąd średniokwadratowy aproksymacji w stosunku do danych testowych
	\item $C$ (ang. Complexity) - złożoność sieci neuronowej mierzona w liczbie połączeń w tej sieci
\end{itemize}
Celem tej polioptymalizacji jest minimalizacja złożoności $C$ oraz minimalizacja błędu aproksymacji $MSE$:

\begin{equation}
min(MSE), min(C)
\end{equation}

**Rozwiązanie**

Badania przedstawione w \cite{intro_neuro} opierają się na badaniu wskaźnika $NPG$ (ang. Normalized Performance Gain) - znormalizowanego przyrostu wydajności. Wskaźnik $NPG$ to stosunek różnicy błędu aproksymacji $MSE$ do przyrostu złożoności $C$ tej sieci:

\begin{equation}
\label{eq:intro_zpw}
NPG = \frac{MSE_j - MSE_i}{C_i - C_j}
\end{equation}

Gdzie indeksy $i$, $j$ odnoszą się do dwóch kolejnych modeli sieci neuronowej o różnej złożoności: $C_j > C_i$.

Wyniki tych badań pokazują, że model optymalny lub bliski optymalnemu można znaleźć poprzez zbadanie zależności wskaźnika $NPG$ od złożoności sieci neuronowej $C$. Dla trzech przedstawionych w \cite{intro_neuro} zbiorów danych optymalna liczba połączeń w sieci neuronowej znajduje się w okolicy najwyższych wartości wskaźnika $NPG$. Dalsze zwiększanie liczby połączeń w sieci neuronowej może powodować nadmierne dopasowanie sieci do zbioru danych treningowych. Koncepcję tego sposobu polioptymalizacji przedstawia rysunek \ref{fig:intro_neuro}.

\begin{figure}[H]
    \centering
    \includegraphics[width=.5\textwidth]{./resources/intro_neuro}
    \caption{Polioptymalizacja modelu sieci neuronowej}
    \label{fig:intro_neuro}
\end{figure}

**Wnioski**

W oparciu o front rozwiązań zadanego problemu polioptymalizacji parametrów $MSE$ oraz $C$ można wyznaczyć zależność wskaźnika $NPG$, który wskaże zakres liczb połączeń w sieci neuronowej, dla których znalezione modele sieci wykazują się aproksymacją obarczoną jak najmniejszym błędem przy zachowaniu możliwie niskiej złożoności.

## Polioptymalizacja w algorytmach klasteryzacji

**Dziedzina**

Polioptymalizacja ma zastosowanie w algorytmach klasteryzacji – opisane w publikacji \cite{intro_cluster}. Istotą problemu klasteryzacji jest dobór liczby klastrów oraz kryterium przyporządkowania do nich punktów. Kryterium przyporządkowania punktów do klastrów może uwzględniać różne parametry, takie jak: gęstość, spójność oraz rozdzielność przestrzenną.

**Opis problemu**

Badania przeprowadzone w \cite{intro_cluster} pokazują, że dobre rozwiązanie powinno uwzględniać wiele parametrów przydziału punktów, ponieważ skupienie się na jednym z nich może doprowadzić do niepoprawnego wyniku klasteryzacji. Dowodem na to jest przykład podany w \cite{intro_cluster}, który pokazuje możliwy efekt skupienia się tylko na jednym parametrze. Jego koncepcję przedstawia oparty na nim rysunek \ref{fig:intro_cluster}.

\begin{figure}[H]
    \centering
    \includegraphics[width=.7\textwidth]{./resources/intro_cluster}
    \caption{Polioptymalizacja w klasteryzacji}
    \label{fig:intro_cluster}
\end{figure}

Na podstawie obserwacji wyników badań \cite{intro_cluster} uogólnionych na rysunku \ref{fig:intro_cluster} można stwierdzić, że pomimo optymalizacji jednej metryki (np. maksymalizacja spójności) rozwiązanie może znacząco różnić się od optymalnego.

Biorąc pod uwagę powyższe rozważania, problem zdefiniowany w \cite{intro_cluster} sprowadza się do problemu znalezienia modelu klasteryzacji (a więc i metryk), który najbardziej odpowiada rozkładowi zbioru danych. Rozwiązaniem tego problemu powinna być metoda klasteryzacji, która dla różnych rozkładów danych w zbiorze będzie dawała wyniki lepsze niż w przypadku optymalizacji jednego parametru.

**Wskaźnik ARI**

Aby ocenić wynik klasteryzacji, można użyć wskaźnika $ARI$ (ang. Adjusted Rand Index) - przyjmujący wartości w przedziale $[-1, 1]$. Jest on miarą podobieństwa między dwoma rozwiązaniami klasteryzacji \cite{intro_ari}. Oblicza się go według wzoru:

\begin{equation}
\label{eq:ari}
ARI(U,V) =
\frac{
    \sum_{lk}\binom{n_{lk}}{2} - [\sum_{l}\binom{n_{l.}}{2} * \sum_{k}\binom{n_{.k}}{2}] / \binom{n}{2}
}
{
    \frac{1}{2} [\sum_{l}\binom{n_{l.}}{2} + \sum_{k}\binom{n_{.k}}{2}] - [\sum_{l}\binom{n_{l.}}{2} * \sum_{k}\binom{n_{.k}}{2}] / \binom{n}{2}
}
\end{equation}

Gdzie $U$, $V$ - dwa porównywane rozwiązania problemu klasteryzacji, czyli dwa podziały zbioru danych $S$ na podzbiory (klastry):

\begin{equation}
U = \{U_1, U_2, ...\}, \bigcup_U U_i = S
\end{equation}

\begin{equation}
V = \{V_1, V_2, ...\}, \bigcup_V V_i = S
\end{equation}

Indeksy $l$ oraz $k$ to numery klastrów w rozwiązaniach odpowiednio $U$ oraz $V$. Wartość $n_{lk}$ oznacza liczbę punktów (danych) które są przypisane jednocześnie do klastra o numerze $l$ w rozwiązaniu $U$ i do klastra o numerze $k$ w rozwiązaniu $V$. Natomiast $n$ to liczba wszystkich punktów w zbiorze danych.

Im większa wartość wskaźnika $ARI$, tym większe jest podobieństwo tych dwóch rozwiązań do siebie. Wyznaczenie jakości rozwiązania można osiągnąć, wyznaczając wartość wskaźnika $ARI$ dla badanego wyniku klasteryzacji oraz rozwiązania referencyjnego, które uważamy za optymalne.

Przykładowo, rozważmy następujące dwa rozwiązania klasteryzacji na rysunku \ref{fig:intro_ari}.

\begin{figure}[H]
    \centering
    \includegraphics[width=.4\textwidth]{./resources/intro_ari}
    \caption{Dwa rozwiązania problemu klasteryzacji - przykładowe oraz badane}
    \label{fig:intro_ari}
\end{figure}

Tabela zawierająca wartości wskaźników $n_{lk}$ dla dwóch rozwiązań klasteryzacji przedstawionych na rysunku \ref{fig:intro_ari} prezentuje się następująco:

\begin{table}[H]
    \centering
    \begin{tabular}{| c | c | c | c | c |}
        \hline
         & $V_1$ & $V_2$ & $V_3$ & $n_{l.}$ \\\hline
        $U_1$ & 3 & 0 & 0 & \textcolor{red}{3} \\\hline
        $U_2$ & 0 & 4 & 3 & \textcolor{red}{7} \\\hline
        $U_3$ & 3 & 0 & 0 & \textcolor{red}{3} \\\hline
        \textcolor{red}{$n_{.k}$} & \textcolor{red}{6} & \textcolor{red}{4} & \textcolor{red}{3} & \\\hline
    \end{tabular}
    \caption{Współczynniki $n_{lk}$ do wzoru na $ARI$}
    \label{tab:intro_ari}
\end{table}

Korzystając ze wzoru \ref{eq:ari}, współczynnik przedstawionych na rysunku \ref{fig:intro_ari} rozwiązań klasteryzacji wynosi:

\begin{equation}
ARI(U,V)
= \frac{ 15 - [27 * 24] / 78 }{ \frac{1}{2} [27 + 24] - [27 * 24] / 78 }
\approx 0,389
\end{equation}

Otrzymana wartość wskaźnika $ARI$ sugeruje, że oba rozwiązania są do siebie w pewnym stopniu podobne (wartość nieujemna), jednak wyraźnie się różnią.

**Rozwiązanie**

Zaproponowane rozwiązanie to algorytm polioptymalizacyjny MOCK (ang. ,,MultiObjective Clustering with automatic K-determination"). Jest to ewolucyjny algorytm polioptymalizacyjny on oparty na solwerze PESA-II. Jego przewagą ma być branie pod uwagę wielu metryk podczas szukania optymalnego rozwiązania.

W ramach publikacji \cite{intro_cluster} zostały przeprowadzone badania jakości rozwiązania problemu klasteryzacji w zależności od rozkładu danych testowych oraz użytego algorytmu klasteryzacji. Przetestowano trzy popularne algorytmy optymalizujące pojedynczy parametr oraz algorytm MOCK. Uśrednione wartości wskaźnika $ARI$ najlepszych rozwiązań testowych problemów dla wszystkich czterech algorytmów opublikowane w \cite{intro_cluster} przedstawia tabela \ref{tab:intro_cluster} (im większa wartość wskaźnika $ARI$ tym lepsze jest rozwiązanie).

\begin{table}[H]
    \centering
    \begin{tabular}{| l | c | r |}
        \hline
        Algorytm & Średnia wartość wskaźnika $ARI$ \\\hline
        MOCK & 0,975 \\\hline
        K-means & 0,72 \\\hline
        Average link & 0,765 \\\hline
        Single link & 0,848 \\\hline
    \end{tabular}
    \caption{Porównanie algorytmów klasteryzacji}
    \label{tab:intro_cluster}
\end{table}

Można zauważyć, że algorytm polioptymalizacyjny MOCK jest o wiele skuteczniejszy w zastosowaniu do klasteryzacji danych o różnych rozkładach niż inne algorytmy bazujące na optymalizacji pojedynczego parametru.

## Polioptymalizacja charakterystyki błędu regresji

**Dziedzina**

Problem aproksymacji został zdefiniowany w \cite{intro_approximation} jako problem oszacowania wartości danego sygnału przez pewien model o pewnych parametrach, przyjmujący niezależne dane wejściowe.

Jakość aproksymacji można określić przez jej średni błąd $\xi$. To podstawowe podejście w wielu przypadkach może być zadowalające, zakładając, że koszt pojedynczego błędu jest liniowy.

**Charakterystyka REC**

Jeśli natura problemu wymaga analizy rozkładu błędu aproksymacji, można zbadać dany model aproksymacji za pomocą charakterystyki $REC$ (ang. Regression Error Characteristics). Charakterystyka ta jest przedstawiana za pomocą wykresu, w którym na osi X znajdują się rosnące wartości błędu aproksymacji, a na osi Y znajduje się udział punktów, dla których błąd aproksymacji jest mniejszy niż wartość na osi X. Wykres charakterystyki $REC$ dla dwóch przykładowych modeli aproksymacji znajduje się na rysunku \ref{fig:intro_rec}.

\begin{figure}[H]
    \centering
    \includegraphics[width=.6\textwidth]{./resources/intro_rec}
    \caption{Charakterystyka $REC$ dwóch przykładowych modeli aproksymacji}
    \label{fig:intro_rec}
\end{figure}

Z wykresu na rysunku \ref{fig:intro_rec} można stwierdzić między innymi, że:
\begin{itemize}
	\item Aproksymacja $M_2$ myli się co najwyżej o $x$ dla wszystkich punktów
	\item Aproksymacja $M_1$ ma maksymalną wartość błędu aproksymacji większą niż aproksymacja $M_2$
	\item Aproksymacja $M_2$ ma mniejszy średni błąd aproksymacji $\xi$ niż aproksymacja $M_1$ (porównując pole ,,nad wykresem", czyli pomiędzy krzywą a linią $y=1$).
\end{itemize}


**Opis problemu**

Istnieją problemy, dla których rozkład wartości błędów jest ważniejszy od średniej wartości. Przykładem ilustrującym przypadek, w którym rozkład błędu ma znaczenie, może być problem aproksymacji popytu na pewien produkt. W tym problemie ważne jest, aby wykorzystany model charakteryzował się jak najniższą wartością maksymalnego błędu aproksymacji. Wynika to z tego, że gdyby w którymś punkcie wartość błędu przekroczyła pewien próg, istnieje ryzyko utraty klientów przez niezaspokojenie popytu na rozważany produkt. Na rysunku \ref{fig:intro_product} zostały przedstawione charakterystyki $REC$ dwóch modeli aproksymacji, które mogłyby być rozważane jako rozwiązanie problemu.

\begin{figure}[H]
    \centering
    \includegraphics[width=.6\textwidth]{./resources/intro_product}
    \caption{Charakterystyka $REC$ przykładowych modeli dla rozwiązania problemu aproksymacji popytu}
    \label{fig:intro_product}
\end{figure}

Na podstawie wykresu na rysunku \ref{fig:intro_product} widać, że spośród dwóch modeli aproksymacji należy wybrać model $M_1$, który minimalizuje ryzyko utraty klientów poprzez utrzymanie maksymalnej wartości błędu poniżej progu $x$. Okazuje się więc, że model $M_1$, który wyraźnie ma większą wartość średniego błędu aproksymacji od modelu $M_2$, jest lepszym wyborem dla naszego problemu.

Problem znalezienia modelu o jak ,,najlepszej" charakterystyce $REC$ jest trudny ze względu na trudność opisania kryteriów, według których ta krzywa ma być oceniana. Mimo to istnieje potrzeba znalezienia charakterystyk $REC$ potencjalnie najlepszych modeli, które będą mogły być empirycznie rozważone w celu wyboru tego modelu, który będzie najlepszy według pewnych subiektywnych kryteriów (np. maksymalna wartość błędu lub wysokie nachylenie krzywej dla niskich wartości błędu).

**Rozwiązanie**

Aby znaleźć front modeli o potencjalnie najlepszych charakterystykach, w \cite{intro_approximation} proponuje się znalezienie modeli o charakterystykach $REC$ niezdominowanych przez inne. Model $M_1$ jest zdominowany przez model $M_2$ gdy jego wykres charakterystyki $REC$ dla $M_1$ leży pod wykresem $M_2$.

W celu znalezienia frontu rozwiązań można wykorzystać algorytm MOEA (ang. Multi-Objective Evolutionary Algorithm). Rysunek \ref{fig:intro_approximation} przedstawia przykładowe rozwiązanie: wykres frontu rozwiązań, jakie osiągnął algorytm oraz jedna z charakterystyk składająca się na ten front (dla niskich wartości błędu aproksymacji).

\begin{figure}[H]
    \centering
    \includegraphics[width=.6\textwidth]{./resources/intro_approximation}
    \caption{Przykładowy wynik polioptymalizacji charakterystyki $REC$}
    \label{fig:intro_approximation}
\end{figure}

**Wnioski**

Badania przedstawione w \cite{intro_approximation} pokazują, że polioptymalizacja pozwala uzyskać zbiór modeli aproksymacji, spośród których można wybrać ten o subiektywnie najlepszej charakterystyce.

# Oprogramowanie CAST

Oprogramowanie CAST jest narzędziem służącym do analizy danych, wspierającym wykrywanie procederu prania brudnych pieniędzy. Integruje ono wiele funkcji służących do analizy oraz wizualizacji różnego rodzaju danych mogących pomóc w wykryciu przestępczej sieci.

## Uruchomienie programu

### Struktura projektu

Oprogramowanie CAST jest zaimplementowane w oparciu o architekturę Eclipse Plugin Architecture. Składa się ono z modułów znajdujących się w trzech katalogach:
\begin{itemize}
    \item castTrunk
    \item linkTrunk
    \item money
\end{itemize}

### Przygotowanie środowiska

Do uruchomienia oprogramowania należy wykorzystać IDE Eclipse Oxygen. Przed przystąpieniem do importu projektu, należy skonfigurować IDE:

\begin{itemize}
    \item Window | Preferences | Java | Installed JREs - należy wybrać JRE 1.8
    \item Window | Preferences | Java | Compiler - ,,Compiler compliance level" powinno mieć wartość 1.8
    \item Window | Preferences | Java | Compiler | Errors/Warnings | Code Style - wartość ,,Non-externalized strings (...)" powinna być ustawiona na Warning
\end{itemize}

### Import projektów do IDE

Następnym krokiem do uruchomienia oprogramowania jest import projektów składowych. W tym celu należy użyć narzędzia spod ,,File | Open Projects from File System...". Dla wszystkich trzech katalogów (castTrunk, linkTrunk, money) należy zaimportować projekty z podkatalogów:
\begin{itemize}
    \item plugins
    \item tests
    \item fragments
    \item features
    \item eclipse-products
\end{itemize}

Przykładowa konfiguracja została przedstawiona na rysunku \ref{fig:cast_1_projects_import}.

\begin{figure}[H]
    \centering
    \includegraphics[width=\textwidth]{./resources/cast_1_projects_import}
    \caption{Przykładowa konfiguracja importu projektów}
    \label{fig:cast_1_projects_import}
\end{figure}

### Kompilacja i uruchomienie

Oprogramowanie powinno być uruchomione za pomocą predefiniowanych konfiguracji uruchomieniowych (Run | Run Configurations...). Dla systemu windows należy wybrać konfigurację ,,cast-windows-oxygen-pl" lub ,,cast-windows-oxygen-pl", natomiast dla systemu linux należy wybrać konfigurację ,,cast-linux-oxygen". Okno konfiguracji powinno wyglądać jak na ryzunku \ref{fig:cast_2_run_conf}.

\begin{figure}[H]
    \centering
    \includegraphics[width=\textwidth]{./resources/cast_2_run_conf}
    \caption{Predefiniowana konfiguracja uruchomieniowa}
    \label{fig:cast_2_run_conf}
\end{figure}

### Stworzenie testowego projektu

Oprogramowanie CAST umożliwia użytkownikowi pracę w ramach oddzielnych projektów. Aby rozpocząć pracę nad nowym projektem, należy użyć narzędzia ,,File | New project...". W pokazanym oknie należy podać ścieżkę do katalogu, gdzie projekty będą zapisane oraz nazwę projektu. Przykładowe okno widnieje na rysunku \ref{fig:cast_3_new_project}.

\begin{figure}[H]
    \centering
    \includegraphics[width=.4\textwidth]{./resources/cast_3_new_project}
    \caption{Okno dodania nowego projektu}
    \label{fig:cast_3_new_project}
\end{figure}

### Dodanie testowych danych o przelewach

Program udostępnia możliwość dodania do projektu testowych zbiorów danych o przelewach. W tym celu należy wykorzystać narzędzie ,,Tools | Add Sample Data Sets...". Po udanym dodaniu danych testowych powinien wyświetlić się komunikat jak na rysunku \ref{fig:cast_5_sample_added}.

\begin{figure}[H]
    \centering
    \includegraphics[width=.5\textwidth]{./resources/cast_5_sample_added}
    \caption{Potwierdzenie dodania danych testowych}
    \label{fig:cast_5_sample_added}
\end{figure}

Narzędzie dodało w tym przypadku 10 zbiorów danych testowych o przelewach bankowych. Ich listę można zobaczyć w pasku bocznym po lewej stronie okna programu - rysunek \ref{fig:cast_6_statements_list}.

\begin{figure}[H]
    \centering
    \includegraphics[width=.3\textwidth]{./resources/cast_6_statements_list}
    \caption{Lista testowych zbiorów danych o przelewach}
    \label{fig:cast_6_statements_list}
\end{figure}

## Wybrane funkcje

### Wizualizacja danych o przelewach bankowych

Zaimportowane dane testowe mogą zostać zaprezentowane w formie grafu, gdzie wierzchołkami są konta bankowe lub właściciele kont bankowych, a krawędziami są przelewy. Aby stworzyć graf dla wybranego zbioru danych - ,,Graph 3 (gathering)" - należy otworzyć jego menu kontekstowe na liście zbiorów danych oraz użyć np. opcji ,,Create diagram | Bank Account Holders to Schema model". Fragment przykładowego diagramu przedstawiającego graf przelewów widnieje na rysunku \ref{fig:cast_7_the_graph}.

\begin{figure}[H]
    \centering
    \includegraphics[width=.5\textwidth]{./resources/cast_7_the_graph}
    \caption{Fragment grafu przelewów bankowych}
    \label{fig:cast_7_the_graph}
\end{figure}


### Wyszukiwanie klastrów

Aby wyszukać klastry wśród grafowej reprezentacji zbioru danych o przelewach, należy otworzyć jego menu kontekstowe na liście zbiorów danych oraz użyć opcji ,,Analysis operations | Find clusters". Po wybraniu opcji pojawi się okno w którym należy wprowadzić parametry wyszukiwania klastrów. Przykładowa konfiguracja zastosowana do zbioru testowego ,,Graph 3 (gathering)" została przedstawiona na rysunku \ref{fig:cast_8_cluster_conf}.

\begin{figure}[H]
    \centering
    \includegraphics[width=.8\textwidth]{./resources/cast_8_cluster_conf}
    \caption{Przykładowa konfiguracja wyszukiwania klastrów}
    \label{fig:cast_8_cluster_conf}
\end{figure}

Wynik wyznaczania klastrów jest zapisywany pod listą zbiorów danych ,,Money clusters of graph structure". Klaster jest przedstawiany jako lista przelewów które składają się na niego. Przykładowy wynik szukania klastrów został przedstawiony na rysunku \ref{fig:cast_9_clusters_results}.

\begin{figure}[H]
    \centering
    \includegraphics[width=\textwidth]{./resources/cast_9_clusters_results}
    \caption{Przykładowy wynik szukania klastrów}
    \label{fig:cast_9_clusters_results}
\end{figure}

### Wzorce częste

Oprogramowanie CAST umożliwia wyszukiwanie wzorców częstych występujących w klastrach znalezionych w zbiorze danych o przelewach. Aby wyszukać wzorce, należy otworzyć menu kontekstowe wyniku szukania klastrów na liście zbiorów danych oraz użyć opcji ,,Analysis operations | Find frequent patterns". Wyświetli się okno konfiguracji parametrów wyszukiwania wzorców częstych. Przykładowa konfiguracja znajduje się na rysunku \ref{fig:cast_10_frequent_parameters}.

\begin{figure}[H]
    \centering
    \includegraphics[width=.9\textwidth]{./resources/cast_10_frequent_parameters}
    \caption{Przykładowa konfiguracja wyszukiwania wzorców częstych}
    \label{fig:cast_10_frequent_parameters}
\end{figure}

Wynikiem wyszukiwania wzorców częstych w klastrach znalezionych wcześniej, są zbiory osób, które biorą udział w znalezionych częstych wzorcach. Dla konfiguracji przedstawionej na rysunku \ref{fig:cast_10_frequent_parameters} wynik wyszukiwania wzorców częstych został przedstawiony na rysunku \ref{fig:cast_11_frequent_results}.

\begin{figure}[H]
    \centering
    \includegraphics[width=.7\textwidth]{./resources/cast_11_frequent_results}
    \caption{Przykładowy wynik wyszukiwania wzorców częstych}
    \label{fig:cast_11_frequent_results}
\end{figure}

### Znajdowanie ról

Oprogramowanie CAST umożliwia analizę sieci społecznościowych. Jedną z funkcji z tej rodziny analiz jest znajdowanie ról w sieci. Role te odzwierciedlają funkcję, jaką dana osoba lub podmiot pełni w sieci społecznościowej. Aby znaleźć role w zbiorze danych o przelewach, należy otworzyć jego menu kontekstowe na liście zbiorów danych oraz użyć np. opcji ,,Analysis operations | Find roles in social network (holders of accounts)". Po wybraniu opcji należy wybrać zbiór ról, który ma zostać wykorzystany - okno na rysunku \ref{fig:cast_12_roles_settings}.

\begin{figure}[H]
    \centering
    \includegraphics[width=\textwidth]{./resources/cast_12_roles_settings}
    \caption{Wybór zbioru ról}
    \label{fig:cast_12_roles_settings}
\end{figure}

Wynikiem wyszukiwania ról w sieci społecznościowej jest lista numerów kont/osób wraz z przypisanymi rolami oraz parametrami ich pozycji w sieci - przykład na rysunku \ref{fig:cast_13_roles_results}.

\begin{figure}[H]
    \centering
    \includegraphics[width=\textwidth]{./resources/cast_13_roles_results}
    \caption{Przykładowy wynik znajdowania ról w sieci społecznościowej}
    \label{fig:cast_13_roles_results}
\end{figure}

\medskip

\begin{thebibliography}{9}
    \bibitem{intro_neuro}
        Y. Jin, B. Sendhoff, \textit{Pareto-based multi-objective machine learning: An overview and case studies}. IEEE Transactions on Systems, Man, and Cybernetics, Part C: Applications and Reviews, 38(3):397-415, 2008
    \bibitem{intro_cluster}
        J. Handl, J. Knowles, \textit{Multi-Objective Clustering and Cluster Validation}. Studies in Computational Intelligence, 16:21-47, 2006, doi:10.1007/3-540-33019-4\char`_2
    \bibitem{intro_approximation}
        Fieldsend J.E. (2006) \textit{Regression Error Characteristic Optimisation of Non-Linear Models}. In: Jin Y. (eds) Multi-Objective Machine Learning. Studies in Computational Intelligence, vol 16. Springer, Berlin, Heidelberg
    \bibitem{intro_ari} Santos J.M., Embrechts M. (2009) \textit{On the Use of the Adjusted Rand Index as a Metric for Evaluating Supervised Classification}. In: Alippi C., Polycarpou M., Panayiotou C., Ellinas G. (eds) Artificial Neural Networks - ICANN 2009. ICANN 2009. Lecture Notes in Computer Science, vol 5769. Springer, Berlin, Heidelberg
\end{thebibliography}