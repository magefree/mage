package mage.cards.j;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.LoyaltyAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.AttackingCreatureCount;
import mage.abilities.effects.common.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.command.emblems.JayaFieryNegotiatorEmblem;
import mage.game.events.GameEvent;
import mage.game.permanent.token.MonkRedToken;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JayaFieryNegotiator extends CardImpl {

    public JayaFieryNegotiator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{R}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.JAYA);
        this.setStartingLoyalty(4);

        // +1: Create a 1/1 red Monk creature token with prowess.
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new MonkRedToken()), 1));

        // −1: Exile the top two cards of your library. Choose one of them. You may play that card this turn.
        this.addAbility(new LoyaltyAbility(new ExileTopXMayPlayUntilEffect(2, true, Duration.EndOfTurn), -1));

        // −2: Choose target creature an opponent controls. Whenever you attack this turn, Jaya, Fiery Negotiator deals damage equal to the number of attacking creatures to that creature.
        Ability ability = new LoyaltyAbility(new CreateDelayedTriggeredAbilityEffect(
                new JayaFieryNegotiatorTriggeredAbility()
        ).setText("choose target creature an opponent controls. Whenever you attack this turn, " +
                "{this} deals damage equal to the number of attacking creatures to that creature"), -2);
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);

        // −8: You get an emblem with "Whenever you cast a red instant or sorcery spell, copy it twice. You may choose new targets for the copies."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new JayaFieryNegotiatorEmblem()), -8));
    }

    private JayaFieryNegotiator(final JayaFieryNegotiator card) {
        super(card);
    }

    @Override
    public JayaFieryNegotiator copy() {
        return new JayaFieryNegotiator(this);
    }
}

class JayaFieryNegotiatorTriggeredAbility extends DelayedTriggeredAbility {

    private static final DynamicValue xValue = new AttackingCreatureCount();

    JayaFieryNegotiatorTriggeredAbility() {
        super(new DamageTargetEffect(xValue), Duration.EndOfTurn, false, false);
    }

    private JayaFieryNegotiatorTriggeredAbility(final JayaFieryNegotiatorTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public JayaFieryNegotiatorTriggeredAbility copy() {
        return new JayaFieryNegotiatorTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return isControlledBy(game.getCombat().getAttackingPlayerId())
                && game
                .getCombat()
                .getAttackers()
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .map(Controllable::getControllerId)
                .anyMatch(this::isControlledBy);
    }

    @Override
    public String getRule() {
        return "Whenever you attack this turn, {this} deals damage equal to the number of attacking creatures to that creature.";
    }
}
