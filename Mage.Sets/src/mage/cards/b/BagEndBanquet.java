package mage.cards.b;

import java.util.UUID;

import mage.Mana;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.mana.DynamicManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.FoodToken;

/**
 *
 * @author muz
 */
public final class BagEndBanquet extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Food you control");

    static {
        filter.add(SubType.FOOD.getPredicate());
    }

    private static final Hint hint = new ValueHint(
        "Food you control", new PermanentsOnBattlefieldCount(filter)
    );

    public BagEndBanquet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{6}");

        // When this artifact enters, create three Food tokens.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new FoodToken(), 3)));

        // {T}: Add {C} for each Food you control.
        this.addAbility(new DynamicManaAbility(
            Mana.ColorlessMana(1),
            new PermanentsOnBattlefieldCount(filter),
            new TapSourceCost()
        ).addHint(hint));
    }

    private BagEndBanquet(final BagEndBanquet card) {
        super(card);
    }

    @Override
    public BagEndBanquet copy() {
        return new BagEndBanquet(this);
    }
}
