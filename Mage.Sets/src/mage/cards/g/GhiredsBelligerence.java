package mage.cards.g;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.abilities.effects.common.PopulateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.target.common.TargetCreaturePermanentAmount;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GhiredsBelligerence extends CardImpl {

    public GhiredsBelligerence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{R}{R}");

        // Ghired's Belligerence deals X damage divided as you choose among any number of target creatures. Whenever a creature dealt damage this way dies this turn, populate.
        this.getSpellAbility().addEffect(new GhiredsBelligerenceEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanentAmount(ManacostVariableValue.instance));
    }

    private GhiredsBelligerence(final GhiredsBelligerence card) {
        super(card);
    }

    @Override
    public GhiredsBelligerence copy() {
        return new GhiredsBelligerence(this);
    }
}

class GhiredsBelligerenceEffect extends OneShotEffect {

    private static final DamageMultiEffect effect = new DamageMultiEffect(ManacostVariableValue.instance);

    GhiredsBelligerenceEffect() {
        super(Outcome.Benefit);
        staticText = "{this} deals X damage divided as you choose among any number of target creatures. " +
                "Whenever a creature dealt damage this way dies this turn, populate.";
    }

    private GhiredsBelligerenceEffect(final GhiredsBelligerenceEffect effect) {
        super(effect);
    }

    @Override
    public GhiredsBelligerenceEffect copy() {
        return new GhiredsBelligerenceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (!effect.apply(game, source)) {
            return false;
        }
        if (effect.getDamagedSet().isEmpty()) {
            return true;
        }
        game.addDelayedTriggeredAbility(new GhiredsBelligerenceDelayedTriggeredAbility(effect.getDamagedSet()), source);
        return true;
    }
}

class GhiredsBelligerenceDelayedTriggeredAbility extends DelayedTriggeredAbility {

    private final Set<MageObjectReference> referenceSet = new HashSet<>();

    GhiredsBelligerenceDelayedTriggeredAbility(Set<MageObjectReference> referenceSet) {
        super(new PopulateEffect(), Duration.EndOfTurn, false);
        this.referenceSet.addAll(referenceSet);
    }

    private GhiredsBelligerenceDelayedTriggeredAbility(final GhiredsBelligerenceDelayedTriggeredAbility ability) {
        super(ability);
        this.referenceSet.addAll(ability.referenceSet);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        return zEvent.isDiesEvent() && referenceSet.stream().anyMatch(mor -> mor.refersTo(zEvent.getTarget(), game));
    }

    @Override
    public GhiredsBelligerenceDelayedTriggeredAbility copy() {
        return new GhiredsBelligerenceDelayedTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever a creature dealt damage this way dies this turn, populate.";
    }
}