package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalCostModificationEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MomoFriendlyFlier extends CardImpl {

    private static final FilterCard filter = new FilterCard();
    private static final FilterPermanent filter2 = new FilterControlledCreaturePermanent("another creature you control with flying");

    static {
        filter.add(Predicates.not(SubType.LEMUR.getPredicate()));
        filter.add(new AbilityPredicate(FlyingAbility.class));
        filter2.add(new AbilityPredicate(FlyingAbility.class));
    }

    public MomoFriendlyFlier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.LEMUR);
        this.subtype.add(SubType.BAT);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // The first non-Lemur creature spell with flying you cast during each of your turns costs {1} less to cast.
        this.addAbility(new SimpleStaticAbility(new ConditionalCostModificationEffect(
                new SpellsCostReductionControllerEffect(filter, 1), MomoFriendlyFlierCondition.instance,
                "the first non-Lemur creature spell with flying you cast during each of your turns costs {1} less to cast"
        )).addHint(MomoFriendlyFlierCondition.getHint()), new MomoFriendlyFlierWatcher());

        // Whenever another creature you control with flying enters, Momo gets +1/+1 until end of turn.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                new BoostSourceEffect(1, 1, Duration.EndOfTurn), filter2
        ));
    }

    private MomoFriendlyFlier(final MomoFriendlyFlier card) {
        super(card);
    }

    @Override
    public MomoFriendlyFlier copy() {
        return new MomoFriendlyFlier(this);
    }
}

enum MomoFriendlyFlierCondition implements Condition {
    instance;
    private static final Hint hint = new ConditionHint(
            instance, "You haven't cast a non-Lemur creature spell with flying during your turn yet"
    );

    public static Hint getHint() {
        return hint;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return game.isActivePlayer(source.getControllerId())
                && !MomoFriendlyFlierWatcher.checkPlayer(game, source);
    }
}

class MomoFriendlyFlierWatcher extends Watcher {

    private final Set<UUID> set = new HashSet<>();

    MomoFriendlyFlierWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.SPELL_CAST) {
            return;
        }
        Spell spell = game.getSpell(event.getTargetId());
        if (spell != null
                && spell.isCreature(game)
                && !spell.hasSubtype(SubType.LEMUR, game)
                && spell.getAbilities(game).containsClass(FlyingAbility.class)) {
            set.add(spell.getControllerId());
        }
    }

    @Override
    public void reset() {
        super.reset();
        set.clear();
    }

    static boolean checkPlayer(Game game, Ability source) {
        return game
                .getState()
                .getWatcher(MomoFriendlyFlierWatcher.class)
                .set
                .contains(source.getControllerId());
    }
}
