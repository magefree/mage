package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.watchers.common.ManaPaidSourceWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SmolderingEgg extends CardImpl {

    public SmolderingEgg(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.DRAGON);
        this.subtype.add(SubType.EGG);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);
        this.transformable = true;
        this.secondSideCardClazz = mage.cards.a.AshmouthDragon.class;

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // Whenever you cast an instant or sorcery spell, put a number of ember counters on Smoldering Egg equal to the amount of mana spent to cast that spell. Then if Smoldering Egg has seven or more ember counters on it, remove them and transform Smoldering Egg.
        this.addAbility(new TransformAbility());
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new SmolderingEggEffect(), StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY, false
        ));
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
        new TransformSourceEffect(true).apply(game, source);
        return true;
    }
}
