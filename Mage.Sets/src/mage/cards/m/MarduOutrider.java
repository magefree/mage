package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability; // not sure about this line - think it is not needed
import mage.abilities.costs.common.DiscardCardCost;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author tiera3 - modified from LesserMasticore
 */
public final class MarduOutrider extends CardImpl {

    public MarduOutrider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");

        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // As an additional cost to cast this spell, discard a card.
        this.getSpellAbility().addCost(new DiscardCardCost());
    }

    private MarduOutrider(final MarduOutrider card) {
        super(card);
    }

    @Override
    public MarduOutrider copy() {
        return new MarduOutrider(this);
    }
}
