package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.target.common.TargetAnyTarget;
import mage.watchers.common.ManaPaidSourceWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SmolderingEgg extends TransformingDoubleFacedCard {

    public SmolderingEgg(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.DRAGON, SubType.EGG}, "{1}{R}",
                "Ashmouth Dragon",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.DRAGON}, "R"
        );
        this.getLeftHalfCard().setPT(0, 4);
        this.getRightHalfCard().setPT(4, 4);

        // Defender
        this.getLeftHalfCard().addAbility(DefenderAbility.getInstance());

        // Whenever you cast an instant or sorcery spell, put a number of ember counters on Smoldering Egg equal to the amount of mana spent to cast that spell. Then if Smoldering Egg has seven or more ember counters on it, remove them and transform Smoldering Egg.
        this.getLeftHalfCard().addAbility(new SpellCastControllerTriggeredAbility(
                new SmolderingEggEffect(), StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY,
                false, SetTargetPointer.SPELL
        ));

        // Ashmouth Dragon
        // Flying
        this.getRightHalfCard().addAbility(FlyingAbility.getInstance());

        // Whenever you cast an instant or sorcery spell, Ashmouth Dragon deals 2 damage to any target.
        Ability ability = new SpellCastControllerTriggeredAbility(
                new DamageTargetEffect(2), StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY, false
        );
        ability.addTarget(new TargetAnyTarget());
        this.getRightHalfCard().addAbility(ability);
    }

    private SmolderingEgg(final SmolderingEgg card) {
        super(card);
    }

    @Override
    public SmolderingEgg copy() {
        return new SmolderingEgg(this);
    }
}

class SmolderingEggEffect extends OneShotEffect {

    SmolderingEggEffect() {
        super(Outcome.Benefit);
        staticText = "put a number of ember counters on {this} equal to the amount of mana spent to cast that spell. " +
                "Then if {this} has seven or more ember counters on it, remove them and transform {this}";
    }

    private SmolderingEggEffect(final SmolderingEggEffect effect) {
        super(effect);
    }

    @Override
    public SmolderingEggEffect copy() {
        return new SmolderingEggEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null) {
            return false;
        }
        Spell spell = (Spell) getValue("spellCast");
        if (spell != null) {
            permanent.addCounters(
                    CounterType.EMBER.createInstance(
                            ManaPaidSourceWatcher.getTotalPaid(spell.getId(), game)
                    ), source.getControllerId(), source, game
            );
        }
        int counters = permanent.getCounters(game).getCount(CounterType.EMBER);
        if (counters < 7) {
            return true;
        }
        permanent.removeCounters(CounterType.EMBER.createInstance(counters), source, game);
        permanent.transform(source, game);
        return true;
    }
}
