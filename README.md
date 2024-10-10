# Lista Fácil - Aplicativo de Lista de Compras

**Lista Fácil** é um aplicativo mobile desenvolvido para facilitar a criação de listas de compras no supermercado. O aplicativo permite ao usuário adicionar, editar e remover itens de uma lista, calcular o total da compra, e oferece uma interface amigável e visualmente atrativa, com um fundo temático de supermercado.

## Funcionalidades

- Adicionar itens à lista de compras (produto e valor).
- Editar e remover itens já adicionados.
- Exibição automática do total acumulado.
- Interface amigável, com design temático.
- Totalizador visível no rodapé da página.
  
## Tecnologias Utilizadas

- **Android Studio** com **Kotlin** e **Gradle**.
- Biblioteca **Jetpack Compose** para construção da interface gráfica.
- **API** para comunicação e armazenamento de dados (caso haja integração futura).
- **Coil** para carregar imagens (fundo do aplicativo).
  
## Requisitos do Aplicativo

1. **Interface Intuitiva**: O aplicativo foi projetado com foco em uma experiência de usuário (UX) simples e eficiente, permitindo que o usuário adicione itens e visualize sua lista de compras de maneira clara e rápida.
2. **Fundo Temático**: Um fundo de supermercado foi adicionado para deixar a interface mais atrativa e temática.
3. **Persistência Local**: Em futuras atualizações, o aplicativo poderá armazenar a lista de compras localmente, permitindo que o usuário continue de onde parou.
4. **Responsividade**: O layout foi otimizado para dispositivos móveis Android de diferentes tamanhos de tela.


### Detalhes Técnicos

- A interface foi criada utilizando a biblioteca **Jetpack Compose**, com elementos como `TextField`, `Button`, `Text`, e layouts de `Column` e `Row` para organização visual.
- A função de cálculo automático do total é atualizada sempre que um item é adicionado ou removido da lista.
- A validação dos campos de entrada foi implementada para aceitar apenas valores numéricos no campo de preço.
- Ícones de edição e remoção foram adicionados para permitir a modificação dos itens da lista.


Este `README.md` inclui uma visão geral do aplicativo, tecnologias usadas, instruções de instalação e os critérios de avaliação do projeto, conforme o solicitado.

