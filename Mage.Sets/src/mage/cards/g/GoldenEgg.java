package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.abilities.token.FoodAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import java.util.UUID;

/**
 * @author jmharmon
 */

public final class GoldenEgg extends CardImpl {

    public GoldenEgg(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");
        this.subtype.add(SubType.FOOD);

        // When Golden Egg enters the battlefield, draw a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1)));

        // {1}, {T}, Sacrifice Golden Egg: Add one mana of any color.
        Ability ability = new AnyColorManaAbility(new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);

        // {2}, {T}, Sacrifice Golden Egg: You gain 3 life.
        this.addAbility(new FoodAbility(true));
    }

    private GoldenEgg(final GoldenEgg card) {
        super(card);
    }

    @Override
    public GoldenEgg copy() {
        return new GoldenEgg(this);
    }
}
