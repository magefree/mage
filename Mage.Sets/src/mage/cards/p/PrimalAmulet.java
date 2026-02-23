package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CopyTargetStackObjectEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterInstantOrSorcerySpell;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PrimalAmulet extends TransformingDoubleFacedCard {

    private static final FilterCard filter = new FilterCard("instant and sorcery spells");

    static {
        filter.add(Predicates.or(
                CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate()
        ));
    }

    public PrimalAmulet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.ARTIFACT}, new SubType[]{}, "{4}",
                "Primal Wellspring",
                new CardType[]{CardType.LAND}, new SubType[]{}, "");

        // Primal Amulet
        // Instant and sorcery spells you cast cost {1} less to cast.
        this.getLeftHalfCard().addAbility(new SimpleStaticAbility(new SpellsCostReductionControllerEffect(filter, 1)));

        // Whenever you cast an instant or sorcery spell, put a charge counter on Primal Amulet. Then if there are four or more charge counters on it, you may remove those counters and transform it.
        Ability ability = new SpellCastControllerTriggeredAbility(
                new AddCountersSourceEffect(CounterType.CHARGE.createInstance()),
                StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY, false
        );
        ability.addEffect(new PrimalAmuletEffect());
        this.getLeftHalfCard().addAbility(ability);

        // Primal Wellspring
        // Add one mana of any color. When that mana is spent to cast an instant or sorcery spell, copy that spell and you may choose new targets for the copy.
        Ability manaAbility = new AnyColorManaAbility();
        manaAbility.addEffect(new CreateDelayedTriggeredAbilityEffect(new PrimalWellspringTriggeredAbility(
                new CopyTargetStackObjectEffect(true)
                        .setText("copy that spell and you may choose new targets for the copy"))
        ));
        this.getRightHalfCard().addAbility(manaAbility);
    }

    private PrimalAmulet(final PrimalAmulet card) {
        super(card);
    }

    @Override
    public PrimalAmulet copy() {
        return new PrimalAmulet(this);
    }
}

class PrimalAmuletEffect extends OneShotEffect {

    PrimalAmuletEffect() {
        super(Outcome.Benefit);
        this.staticText = "Then if there are four or more charge counters on it, " +
                "you may remove those counters and transform it";
    }

    private PrimalAmuletEffect(final PrimalAmuletEffect effect) {
        super(effect);
    }

    @Override
    public PrimalAmuletEffect copy() {
        return new PrimalAmuletEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null
                || player == null
                || permanent.getCounters(game).getCount(CounterType.CHARGE) <= 3
                || !player.chooseUse(Outcome.Benefit, "Remove all charge counters from this and transform it?", source, game)) {
            return false;
        }
        permanent.removeAllCounters(CounterType.CHARGE.getName(), source, game);
        permanent.transform(source, game);
        return true;
    }
}

class PrimalWellspringTriggeredAbility extends DelayedTriggeredAbility {

    private static final FilterInstantOrSorcerySpell filter = new FilterInstantOrSorcerySpell();


    public PrimalWellspringTriggeredAbility(Effect effect) {
        super(effect, Duration.Custom, true, false);
        setTriggerPhrase("When that mana is spent to cast an instant or sorcery spell, ");
    }

    private PrimalWellspringTriggeredAbility(final PrimalWellspringTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public PrimalWellspringTriggeredAbility copy() {
        return new PrimalWellspringTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.MANA_PAID;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getSourceId().equals(getSourceId())) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell != null && filter.match(spell, getControllerId(), this, game)) {
                for (Effect effect : getEffects()) {
                    effect.setTargetPointer(new FixedTarget(event.getTargetId()));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isInactive(Game game) {
        if (super.isInactive(game)) {
            return true;
        }

        // must remove effect on empty mana pool to fix accumulate bug
        Player player = game.getPlayer(this.getControllerId());
        if (player == null) {
            return true;
        }

        // if no mana in pool then it can be discarded
        return player.getManaPool().getManaItems().stream().noneMatch(m -> m.getSourceId().equals(getSourceId()));
    }
}
