package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChargingStrifeknight extends CardImpl {

    public ChargingStrifeknight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // {T}, Discard a card: Draw a card.
        Ability ability = new SimpleActivatedAbility(new DrawCardSourceControllerEffect(1), new TapSourceCost());
        ability.addCost(new DiscardCardCost());
        this.addAbility(ability);
    }

    private ChargingStrifeknight(final ChargingStrifeknight card) {
        super(card);
    }

    @Override
    public ChargingStrifeknight copy() {
        return new ChargingStrifeknight(this);
    }
}
