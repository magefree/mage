package mage.cards.t;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.FightTargetSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.DefendingPlayerControlsPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.TargetPermanent;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheTarrasque extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("creature defending player controls");

    static {
        filter.add(DefendingPlayerControlsPredicate.instance);
    }

    public TheTarrasque(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{G}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(10);
        this.toughness = new MageInt(10);

        // The Tarrasque has haste and ward {10} as long as it was cast.
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(
                        HasteAbility.getInstance(), Duration.WhileOnBattlefield
                ), TheTarrasqueCondition.instance, "{this} has haste"
        ));
        ability.addEffect(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(
                        new WardAbility(new GenericManaCost(10)), Duration.WhileOnBattlefield
                ), TheTarrasqueCondition.instance, "and ward {10} as long as it was cast"
        ));
        this.addAbility(ability, new TheTarrasqueWatcher());

        // Whenever The Tarrasque attacks, it fights target creature defending player controls.
        ability = new AttacksTriggeredAbility(new FightTargetSourceEffect()
                .setText("it fights target creature defending player controls"));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private TheTarrasque(final TheTarrasque card) {
        super(card);
    }

    @Override
    public TheTarrasque copy() {
        return new TheTarrasque(this);
    }
}

enum TheTarrasqueCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return TheTarrasqueWatcher.checkSource(game, source);
    }
}

class TheTarrasqueWatcher extends Watcher {

    private final Set<MageObjectReference> cast = new HashSet<>();

    TheTarrasqueWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        switch (event.getType()) {
            case SPELL_CAST:
                Spell spell = game.getSpell(event.getTargetId());
                if (spell != null) {
                    cast.add(new MageObjectReference(spell.getCard(), game, 1));
                }
                return;
            case BEGINNING_PHASE_PRE:
                if (game.getTurnNum() == 1) {
                    cast.clear();
                }
        }
    }

    static boolean checkSource(Game game, Ability source) {
        TheTarrasqueWatcher watcher = game.getState().getWatcher(TheTarrasqueWatcher.class);
        return watcher != null
                && watcher
                .cast
                .stream()
                .anyMatch(mor -> mor.refersTo(source.getSourcePermanentIfItStillExists(game), game));
    }
}
