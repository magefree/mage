package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.BecomesTappedSourceTriggeredAbility;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MechanNavigator extends CardImpl {

    public MechanNavigator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.ROBOT);
        this.subtype.add(SubType.PILOT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever this creature becomes tapped, draw a card, then discard a card.
        this.addAbility(new BecomesTappedSourceTriggeredAbility(new DrawDiscardControllerEffect(1, 1)));
    }

    private MechanNavigator(final MechanNavigator card) {
        super(card);
    }

    @Override
    public MechanNavigator copy() {
        return new MechanNavigator(this);
    }
}
