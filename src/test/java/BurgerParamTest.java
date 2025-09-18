package praktikum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static praktikum.TestConstants.*;

@ExtendWith(MockitoExtension.class)
public class BurgerParamTest {

    private Burger burger;

    @Mock
    private Bun bun;
    @Mock
    private Ingredient ingredient;

    @BeforeEach
    public void setUp() {
        burger = new Burger();
    }

    private static Stream<Arguments> ingredientProvider() {
        return Stream.of(
                Arguments.of(IngredientType.SAUCE, HOT_SAUCE_NAME, SAUCE_PRICE, "sauce"),
                Arguments.of(IngredientType.FILLING, CUTLET_NAME, FILLING_PRICE, "filling"),
                Arguments.of(IngredientType.SAUCE, CHILI_SAUCE_NAME, CHILI_SAUCE_PRICE, "sauce")
        );
    }

    @ParameterizedTest(name = "Тип: {0}, Ингредиент: {1}")
    @MethodSource("ingredientProvider")
    public void testGetReceiptWithDifferentIngredients(IngredientType type, String name,
                                                       float price, String expectedTypeString) {
        when(bun.getName()).thenReturn(BLACK_BUN_NAME);
        when(bun.getPrice()).thenReturn(BUN_PRICE);
        when(ingredient.getType()).thenReturn(type);
        when(ingredient.getName()).thenReturn(name);
        when(ingredient.getPrice()).thenReturn(price);

        burger.setBuns(bun);
        burger.addIngredient(ingredient);

        String expected = String.format(RECEIPT_STRUCTURE,
                BLACK_BUN_NAME, expectedTypeString, name, BLACK_BUN_NAME, BUN_PRICE * 2 + price);
        assertEquals(expected, burger.getReceipt(),
                String.format("Чек для %s должен содержать правильный тип (%s)", name, expectedTypeString));
    }

    @ParameterizedTest(name = "Тип: {0}, Ингредиент: {1}")
    @MethodSource("ingredientProvider")
    public void testGetPriceWithDifferentIngredients(IngredientType type, String name,
                                                     float price, String expectedTypeString) {
        when(bun.getPrice()).thenReturn(BUN_PRICE);
        when(ingredient.getPrice()).thenReturn(price);

        burger.setBuns(bun);
        burger.addIngredient(ingredient);

        float expectedPrice = BUN_PRICE * 2 + price;
        assertEquals(expectedPrice, burger.getPrice(), 0.0,
                String.format("Цена для %s должна быть %.2f", name, expectedPrice));
    }
}