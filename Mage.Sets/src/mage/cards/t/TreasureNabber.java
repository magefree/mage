package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;
import mage.target.targetpointer.FixedTargets;

import java.util.List;
import java.util.UUID;

/**
 * @author spjspj
 */
public final class TreasureNabber extends CardImpl {

    public TreasureNabber(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever an opponent taps an artifact for mana, gain control of that artifact until the end of your next turn.
        this.addAbility(new TreasureNabberAbility());
    }

    public TreasureNabber(final TreasureNabber card) {
        super(card);
    }

    @Override
    public TreasureNabber copy() {
        return new TreasureNabber(this);
    }
}

class TreasureNabberAbility extends TriggeredAbilityImpl {

    public TreasureNabberAbility() {
        super(Zone.BATTLEFIELD, new TreasureNabberEffect());
    }

    public TreasureNabberAbility(TreasureNabberAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.TAPPED_FOR_MANA
                && !game.inCheckPlayableState();
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getOpponents(controllerId).contains(event.getPlayerId())) {
            Permanent permanent = game.getPermanent(event.getSourceId());
            if (permanent != null && permanent.isArtifact()) {
                getEffects().get(0).setTargetPointer(new FixedTarget(permanent, game));
                return true;
            }
        }
        return false;
    }

    @Override
    public TreasureNabberAbility copy() {
        return new TreasureNabberAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever an opponent taps an artifact for mana, gain control of that artifact until the end of your next turn";
    }
}

class TreasureNabberEffect extends ContinuousEffectImpl {

    protected FixedTargets fixedTargets;

    TreasureNabberEffect() {
        super(Duration.UntilEndOfYourNextTurn, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
        this.staticText = "gain control of that artifact until the end of your next turn";
    }

    TreasureNabberEffect(final TreasureNabberEffect effect) {
        super(effect);
        this.fixedTargets = effect.fixedTargets;
    }

    @Override
    public TreasureNabberEffect copy() {
        return new TreasureNabberEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));

        if (permanent != null) {
            permanent.changeControllerId(source.getControllerId(), game);
            return true;
        }
        return false;
    }

    public void setTargets(List<Permanent> targetedPermanents, Game game) {
        this.fixedTargets = new FixedTargets(targetedPermanents, game);
    }
}
