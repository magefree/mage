package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.BlightCost;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GristleGlutton extends CardImpl {

    public GristleGlutton(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {T}, Blight 1: Discard a card. If you do, draw a card.
        Ability ability = new SimpleActivatedAbility(new DoIfCostPaid(
                new DrawCardSourceControllerEffect(1), new DiscardCardCost(), null, false
        ), new TapSourceCost());
        ability.addCost(new BlightCost(1));
        this.addAbility(ability);
    }

    private GristleGlutton(final GristleGlutton card) {
        super(card);
    }

    @Override
    public GristleGlutton copy() {
        return new GristleGlutton(this);
    }
}
