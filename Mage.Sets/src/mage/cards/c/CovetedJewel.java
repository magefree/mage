package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CovetedJewel extends CardImpl {

    public CovetedJewel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{6}");

        // When Coveted Jewel enters the battlefield, draw three cards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new DrawCardSourceControllerEffect(3)
        ));

        // {T}: Add three mana of any one color.
        this.addAbility(new SimpleManaAbility(
                Zone.BATTLEFIELD,
                new AddManaOfAnyColorEffect(3),
                new TapSourceCost()
        ));

        // Whenever one or more creatures an opponent controls attack you and aren't blocked, that player draws three cards and gains control of Coveted Jewel. Untap it.
        this.addAbility(new CovetedJewelTriggeredAbility());
    }

    private CovetedJewel(final CovetedJewel card) {
        super(card);
    }

    @Override
    public CovetedJewel copy() {
        return new CovetedJewel(this);
    }
}

class CovetedJewelTriggeredAbility extends TriggeredAbilityImpl {

    public CovetedJewelTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardTargetEffect(3), false);
        this.addEffect(new CovetedJewelControlEffect());
        this.addEffect(new UntapSourceEffect());
    }

    private CovetedJewelTriggeredAbility(final CovetedJewelTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CovetedJewelTriggeredAbility copy() {
        return new CovetedJewelTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARE_BLOCKERS_STEP; // this won't work correctly if multiple players can attack at the same time (what's currently not possible)
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Player currentController = game.getPlayer(getControllerId());
        if (currentController == null) {
            return false;
        }
        for (UUID attacker : game.getCombat().getAttackers()) {
            Permanent attackingCreature = game.getPermanent(attacker);
            if (attackingCreature != null
                    && currentController.hasOpponent(attackingCreature.getControllerId(), game)
                    && getControllerId().equals(game.getCombat().getDefenderId(attacker)) // does not trigger if planeswalker is attacked
                    && !attackingCreature.isBlocked(game)) {
                this.getEffects().setTargetPointer(new FixedTarget(event.getPlayerId(), game));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever one or more creatures an opponent controls attack you "
                + "and aren't blocked, that player draws three cards "
                + "and gains control of {this}. Untap it.";
    }
}

class CovetedJewelControlEffect extends ContinuousEffectImpl {

    public CovetedJewelControlEffect() {
        super(Duration.Custom, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
    }

    private CovetedJewelControlEffect(final CovetedJewelControlEffect effect) {
        super(effect);
    }

    @Override
    public CovetedJewelControlEffect copy() {
        return new CovetedJewelControlEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        Player newControllingPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (permanent == null || newControllingPlayer == null || !newControllingPlayer.canRespond()) {
            this.discard();
            return false;
        }
        permanent.changeControllerId(getTargetPointer().getFirst(game, source), game, source);
        return true;
    }
}
