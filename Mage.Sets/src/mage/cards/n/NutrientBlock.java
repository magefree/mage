package mage.cards.n;

import mage.abilities.common.PutIntoGraveFromBattlefieldSourceTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.token.FoodAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NutrientBlock extends CardImpl {

    public NutrientBlock(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        this.subtype.add(SubType.FOOD);

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());

        // {2}, {T}, Sacrifice this artifact: You gain 3 life.
        this.addAbility(new FoodAbility());

        // When this artifact is put into a graveyard from the battlefield, draw a card.
        this.addAbility(new PutIntoGraveFromBattlefieldSourceTriggeredAbility(new DrawCardSourceControllerEffect(1)));
    }

    private NutrientBlock(final NutrientBlock card) {
        super(card);
    }

    @Override
    public NutrientBlock copy() {
        return new NutrientBlock(this);
    }
}
