package mage.cards.i;

import mage.MageInt;
import mage.abilities.common.BecomesTappedSourceTriggeredAbility;
import mage.abilities.common.CrewSaddleWithToughnessAbility;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InterfaceAce extends CardImpl {

    public InterfaceAce(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.ROBOT);
        this.subtype.add(SubType.PILOT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // This creature saddles Mounts and crews Vehicles using its toughness rather than its power.
        this.addAbility(CrewSaddleWithToughnessAbility.getInstance());

        // Whenever this creature becomes tapped during your turn, untap it. This ability triggers only once each turn.
        this.addAbility(new InterfaceAceTriggeredAbility());
    }

    private InterfaceAce(final InterfaceAce card) {
        super(card);
    }

    @Override
    public InterfaceAce copy() {
        return new InterfaceAce(this);
    }
}

class InterfaceAceTriggeredAbility extends BecomesTappedSourceTriggeredAbility {

    InterfaceAceTriggeredAbility() {
        super(new UntapSourceEffect().setText("untap it"));
        setTriggerPhrase("Whenever this creature becomes tapped during your turn, ");
        setTriggersLimitEachTurn(1);
    }

    private InterfaceAceTriggeredAbility(final InterfaceAceTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public InterfaceAceTriggeredAbility copy() {
        return new InterfaceAceTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return game.isActivePlayer(getControllerId()) && super.checkTrigger(event, game);
    }
}
