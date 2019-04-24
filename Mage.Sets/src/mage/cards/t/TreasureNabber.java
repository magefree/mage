package mage.cards.t;

import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.constants.SubLayer;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;
import mage.target.targetpointer.FixedTargets;

/**
 *
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
        return event.getType() == EventType.TAPPED_FOR_MANA;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getOpponents(controllerId).contains(event.getPlayerId())) {
            Permanent permanent = game.getPermanent(event.getSourceId());
            if (permanent != null && permanent.isArtifact()) {
                getEffects().get(0).setTargetPointer(new FixedTarget(permanent.getId()));
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
    protected int startingTurn;

    TreasureNabberEffect() {
        super(Duration.Custom, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
        this.staticText = "gain control of that artifact until the end of your next turn";
        startingTurn = 0;
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        startingTurn = game.getTurnNum();
        if (game.getPhase().getStep().getType() == PhaseStep.END_TURN) {
            startingTurn = game.getTurnNum() + 1;
        }
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
    public boolean isInactive(Ability source, Game game) {
        if (startingTurn != 0 && game.getTurnNum() >= startingTurn && game.getPhase().getStep().getType() == PhaseStep.END_TURN) {
            if (game.isActivePlayer(source.getControllerId())) {
                return true;
            }
        }
        return false;
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
