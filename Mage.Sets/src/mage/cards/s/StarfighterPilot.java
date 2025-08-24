package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.BecomesTappedSourceTriggeredAbility;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StarfighterPilot extends CardImpl {

    public StarfighterPilot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PILOT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever this creature becomes tapped, surveil 1.
        this.addAbility(new BecomesTappedSourceTriggeredAbility(new SurveilEffect(1)));
    }

    private StarfighterPilot(final StarfighterPilot card) {
        super(card);
    }

    @Override
    public StarfighterPilot copy() {
        return new StarfighterPilot(this);
    }
}
