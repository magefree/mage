package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.MyrToken;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class MyrBattlesphere extends CardImpl {

    public MyrBattlesphere(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{7}");
        this.subtype.add(SubType.MYR);
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(7);

        // When Myr Battlesphere enters the battlefield, create four 1/1 colorless Myr artifact creature tokens.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new MyrToken(), 4), false));

        // Whenever Myr Battlesphere attacks, you may tap X untapped Myr you control. If you do, Myr Battlesphere gets +X/+0 until end of turn and deals X damage to defending player.
        this.addAbility(new MyrBattlesphereTriggeredAbility());

    }

    private MyrBattlesphere(final MyrBattlesphere card) {
        super(card);
    }

    @Override
    public MyrBattlesphere copy() {
        return new MyrBattlesphere(this);
    }

}

class MyrBattlesphereTriggeredAbility extends TriggeredAbilityImpl {

    public MyrBattlesphereTriggeredAbility() {
        super(Zone.BATTLEFIELD, new MyrBattlesphereEffect(), true);
    }

    public MyrBattlesphereTriggeredAbility(final MyrBattlesphereTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public MyrBattlesphereTriggeredAbility copy() {
        return new MyrBattlesphereTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent source = game.getPermanent(event.getSourceId());
        if (source != null && source.getId().equals(this.getSourceId())) {
            UUID defenderId = game.getCombat().getDefenderId(event.getSourceId());
            this.getEffects().get(0).setTargetPointer(new FixedTarget(defenderId, game));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} attacks, you may tap X untapped Myr you control. If you do, {this} gets +X/+0 until end of turn and deals X damage to the player or planeswalker it's attacking.";
    }
}

class MyrBattlesphereEffect extends OneShotEffect {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("untapped Myr you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
        filter.add(SubType.MYR.getPredicate());
    }

    public MyrBattlesphereEffect() {
        super(Outcome.Damage);
        staticText = "you may tap X untapped Myr you control. If you do, {this} gets +X/+0 until end of turn and deals X damage to the player or planeswalker it's attacking.";
    }

    public MyrBattlesphereEffect(final MyrBattlesphereEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent myr = game.getPermanentOrLKIBattlefield(source.getSourceId());
            int tappedAmount = 0;
            TargetPermanent target = new TargetPermanent(0, 1, filter, true);
            while (controller.canRespond()) {
                target.clearChosen();
                if (target.canChoose(source.getControllerId(), source, game)) {
                    Map<String, Serializable> options = new HashMap<>();
                    options.put("UI.right.btn.text", "Myr tapping complete");
                    controller.choose(outcome, target, source, game, options);
                    if (!target.getTargets().isEmpty()) {
                        UUID creature = target.getFirstTarget();
                        if (creature != null) {
                            game.getPermanent(creature).tap(source, game);
                            tappedAmount++;
                        }
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }
            if (tappedAmount > 0) {
                game.informPlayers(controller.getLogName() + " taps " + tappedAmount + " Myrs");
                // boost effect
                game.addEffect(new BoostSourceEffect(tappedAmount, 0, Duration.EndOfTurn), source);
                // damage to defender
                return game.damagePlayerOrPlaneswalker(targetPointer.getFirst(game, source), tappedAmount, myr.getId(), source, game, false, true) > 0;
            }
            return true;
        }
        return false;
    }

    @Override
    public MyrBattlesphereEffect copy() {
        return new MyrBattlesphereEffect(this);
    }

}
