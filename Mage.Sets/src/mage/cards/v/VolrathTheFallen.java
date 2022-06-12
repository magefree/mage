package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.DiscardCostCardManaValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class VolrathTheFallen extends CardImpl {

    public VolrathTheFallen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}{B}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(6);
        this.toughness = new MageInt(4);

        // {1}{B}, Discard a creature card:
        // Volrath the Fallen gets +X/+X until end of turn, where X is the discarded card's converted mana cost.
        Effect effect = new BoostSourceEffect(DiscardCostCardManaValue.instance, DiscardCostCardManaValue.instance, Duration.EndOfTurn);
        effect.setText("{this} gets +X/+X until end of turn, where X is the discarded card's mana value");

        Ability ability = new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                effect,
                new ManaCostsImpl<>("{1}{B}"));
        ability.addCost(new DiscardCardCost(StaticFilters.FILTER_CARD_CREATURE));
        this.addAbility(ability);
    }

    private VolrathTheFallen(final VolrathTheFallen card) {
        super(card);
    }

    @Override
    public VolrathTheFallen copy() {
        return new VolrathTheFallen(this);
    }
}
