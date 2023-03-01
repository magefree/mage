package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfYourEndStepTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.keyword.SunburstAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.Counter;
import mage.filter.FilterPermanent;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
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
        this.addAbility(new SpellCastControllerTriggeredAbility(new LuxArtilleryEffect(this), filter, false, true));

        // At the beginning of your end step, if there are thirty or more counters among artifacts
        // and creatures you control, Lux Artillery deals 10 damage to each opponent.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility (new BeginningOfYourEndStepTriggeredAbility(
                Zone.BATTLEFIELD, new DamagePlayersEffect(10, TargetController.OPPONENT), false), LuxArtilleryCondition.instance,
                "At the beginning of your end step, if there are thirty or more counters among artifacts " +
                        "and creatures you control, {this} deals 10 damage to each opponent"
        ));
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

    private final Ability ability;
    private int zoneChangeCounter;
    private UUID permanentId;

    LuxArtilleryEffect(Card card) {
        super(Duration.OneUse, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "it gains sunburst. <i>(It enters the battlefield with a +1/+1 counter on it for each color of mana spent to cast it.)</i>";
        ability = new SunburstAbility(card);
    }

    private LuxArtilleryEffect(final LuxArtilleryEffect effect) {
        super(effect);
        this.ability = effect.ability.copy();
        this.zoneChangeCounter = effect.zoneChangeCounter;
        this.permanentId = effect.permanentId;
    }

    @Override
    public LuxArtilleryEffect copy() {
        return new LuxArtilleryEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        Spell object = game.getStack().getSpell(targetPointer.getFirst(game, source));
        if (object != null) {
            zoneChangeCounter = game.getState().getZoneChangeCounter(object.getSourceId()) + 1;
            permanentId = object.getSourceId();
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(permanentId);
        if (permanent != null && permanent.getZoneChangeCounter(game) <= zoneChangeCounter) {
            permanent.addAbility(new SunburstAbility(permanent), source.getSourceId(), game);
        } else {
            if (game.getState().getZoneChangeCounter(permanentId) >= zoneChangeCounter) {
                discard();
            }
            Spell spell = game.getStack().getSpell(targetPointer.getFirst(game, source));
            if (spell != null) {
                game.getState().addOtherAbility(spell.getCard(), ability, true);
            }
        }
        return true;
    }
}

enum LuxArtilleryCondition implements Condition {
    instance;

    private static final FilterPermanent filter = new FilterPermanent("artifacts and creatures you control");

    static {
        filter.add(Predicates.or(CardType.ARTIFACT.getPredicate(), CardType.CREATURE.getPredicate()));
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int totalCounters = 0;
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
            if (permanent == null) {
                continue;
            }
            for (Counter counter : permanent.getCounters(game).values()) {
                totalCounters += counter.getCount();
            }
        }
        return totalCounters >= 30;
    }

    @Override
    public String toString() {
        return "there are thirty or more counters among artifacts and creatures you control";
    }
}
