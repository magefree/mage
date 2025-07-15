package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.keyword.SunburstAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.Counters;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class LuxArtillery extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("an artifact creature spell");

    static {
        filter.add(CardType.ARTIFACT.getPredicate());
        filter.add(CardType.CREATURE.getPredicate());
    }

    public LuxArtillery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // Whenever you cast an artifact creature spell, it gains sunburst.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new LuxArtilleryEffect(), filter, false, SetTargetPointer.SPELL
        ));

        // At the beginning of your end step, if there are thirty or more counters among artifacts and creatures you control, Lux Artillery deals 10 damage to each opponent.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new DamagePlayersEffect(10, TargetController.OPPONENT)
        ).withInterveningIf(LuxArtilleryCondition.instance));
    }

    private LuxArtillery(final LuxArtillery card) {
        super(card);
    }

    @Override
    public LuxArtillery copy() {
        return new LuxArtillery(this);
    }
}

class LuxArtilleryEffect extends ContinuousEffectImpl {

    private UUID permanentId;
    private int zoneChangeCounter;

    LuxArtilleryEffect() {
        super(Duration.OneUse, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "it gains sunburst. <i>(It enters the battlefield with a +1/+1 counter on it for each color of mana spent to cast it.)</i>";
    }

    private LuxArtilleryEffect(final LuxArtilleryEffect effect) {
        super(effect);
        this.permanentId = effect.permanentId;
        this.zoneChangeCounter = effect.zoneChangeCounter;
    }

    @Override
    public LuxArtilleryEffect copy() {
        return new LuxArtilleryEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        Spell object = game.getSpell(getTargetPointer().getFirst(game, source));
        if (object != null) {
            permanentId = object.getSourceId();
            zoneChangeCounter = game.getState().getZoneChangeCounter(object.getSourceId()) + 1;
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (game.getState().getZoneChangeCounter(permanentId) >= zoneChangeCounter) {
            discard();
            return false;
        }
        Permanent permanent = game.getPermanent(permanentId);
        if (permanent != null && permanent.getZoneChangeCounter(game) <= zoneChangeCounter) {
            permanent.addAbility(new SunburstAbility(permanent), source.getSourceId(), game);
            return true;
        }
        Spell spell = game.getStack().getSpell(getTargetPointer().getFirst(game, source));
        if (spell != null) {
            game.getState().addOtherAbility(spell.getCard(), new SunburstAbility(spell), true);
        }
        return true;
    }
}

enum LuxArtilleryCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return game
                .getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT_OR_CREATURE,
                        source.getControllerId(), source, game
                )
                .stream()
                .map(permanent -> permanent.getCounters(game))
                .mapToInt(Counters::getTotalCount)
                .sum() >= 30;
    }

    @Override
    public String toString() {
        return "there are thirty or more counters among artifacts and creatures you control";
    }
}
