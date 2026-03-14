package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.condition.common.ManaWasSpentCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class Firespout extends CardImpl {

    private static final FilterCreaturePermanent filter1 = new FilterCreaturePermanent("creature without flying");

    static {
        filter1.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    public Firespout(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R/G}");

        // Firespout deals 3 damage to each creature without flying if {R} was spent to cast Firespout and 3 damage to each creature with flying if {G} was spent to cast it.
        this.getSpellAbility().addEffect(new FirespoutEffect());

    }

    private Firespout(final Firespout card) {
        super(card);
    }

    @Override
    public Firespout copy() {
        return new Firespout(this);
    }
}

// needed for simultaneous damage
class FirespoutEffect extends OneShotEffect {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature without flying");
    static {
        filter.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    FirespoutEffect() {
        super(Outcome.Benefit);
        staticText = "{this} deals 3 damage to each creature without flying if {R} was spent to cast this spell " +
                "and 3 damage to each creature with flying if {G} was spent to cast this spell. " +
                "<i>(Do both if {R}{G} was spent.)</i>";
    }

    private FirespoutEffect(final FirespoutEffect effect) {
        super(effect);
    }

    @Override
    public FirespoutEffect copy() {
        return new FirespoutEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        new ConditionalOneShotEffect(new DamageAllEffect(3, filter),
                ManaWasSpentCondition.RED).apply(game, source);
        new ConditionalOneShotEffect(new DamageAllEffect(3, StaticFilters.FILTER_CREATURE_FLYING),
                ManaWasSpentCondition.GREEN).apply(game, source);
        return true;
    }
}
