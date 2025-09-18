package praktikum;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static praktikum.TestConstants.*;

@ExtendWith(MockitoExtension.class)
public class BurgerTest {

    private Burger burger;
    private static final int FIRST_POSITION = 0;
    private static final int SECOND_POSITION = 1;

    @Mock
    private Bun bun;
    @Mock
    private Ingredient mockIngredient;
    @Mock
    private Ingredient extraMockIngredient;

    @BeforeEach
    public void setUp() {
        burger = new Burger();
    }

    @Test
    public void testAddIngredientIncreasesSize() {
        int initialSize = burger.ingredients.size();
        burger.addIngredient(mockIngredient);
        assertEquals(initialSize + 1, burger.ingredients.size(),
                "Количество ингредиентов должно увеличиться на 1");
    }

    @Test
    public void testAddedIngredientIsPresent() {
        burger.addIngredient(mockIngredient);
        assertTrue(burger.ingredients.contains(mockIngredient),
                "Добавленный ингредиент должен присутствовать в бургере");
    }

    @Test
    public void testAddMultipleIngredients() {
        burger.addIngredient(mockIngredient);
        burger.addIngredient(extraMockIngredient);
        assertEquals(2, burger.ingredients.size(),
                "Должно быть 2 ингредиента");
    }

    @Test
    public void testRemoveIngredient() {
        burger.addIngredient(mockIngredient);
        burger.removeIngredient(FIRST_POSITION);
        assertTrue(burger.ingredients.isEmpty(),
                "Список ингредиентов должен быть пуст после удаления");
    }

    @Test
    public void testMoveIngredientChangesFirstPosition() {
        burger.addIngredient(mockIngredient);
        burger.addIngredient(extraMockIngredient);
        burger.moveIngredient(FIRST_POSITION, SECOND_POSITION);
        assertEquals(mockIngredient, burger.ingredients.get(SECOND_POSITION),
                "Ингредиент должен переместиться на вторую позицию");
    }

    @Test
    public void testMoveIngredientChangesSecondPosition() {
        burger.addIngredient(mockIngredient);
        burger.addIngredient(extraMockIngredient);
        burger.moveIngredient(FIRST_POSITION, SECOND_POSITION);
        assertEquals(extraMockIngredient, burger.ingredients.get(FIRST_POSITION),
                "Второй ингредиент должен переместиться на первую позицию");
    }

    @Test
    public void testGetPrice() {
        when(bun.getPrice()).thenReturn(BUN_PRICE);
        when(mockIngredient.getPrice()).thenReturn(SAUCE_PRICE);
        when(extraMockIngredient.getPrice()).thenReturn(FILLING_PRICE);

        burger.setBuns(bun);
        burger.addIngredient(mockIngredient);
        burger.addIngredient(extraMockIngredient);

        float expectedPrice = BUN_PRICE * 2 + SAUCE_PRICE + FILLING_PRICE;
        assertEquals(expectedPrice, burger.getPrice(), 0.0,
                "Общая цена должна учитывать булочку (x2) и все ингредиенты");
    }

    @Test
    public void testGetReceipt() {
        when(bun.getName()).thenReturn(BLACK_BUN_NAME);
        when(bun.getPrice()).thenReturn(BUN_PRICE);
        when(mockIngredient.getType()).thenReturn(IngredientType.SAUCE);
        when(mockIngredient.getName()).thenReturn(HOT_SAUCE_NAME);
        when(mockIngredient.getPrice()).thenReturn(SAUCE_PRICE);

        burger.setBuns(bun);
        burger.addIngredient(mockIngredient);

        String expectedReceipt = String.format(RECEIPT_STRUCTURE,
                BLACK_BUN_NAME, "sauce", HOT_SAUCE_NAME, BLACK_BUN_NAME, BUN_PRICE * 2 + SAUCE_PRICE);
        assertEquals(expectedReceipt, burger.getReceipt(),
                "Чек должен содержать корректную информацию о составе");
    }
}