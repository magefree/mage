package mage.cards.a;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author spjspj
 */
public final class ArixmethesSlumberingIsle extends CardImpl {

    private static final Condition condition
            = new SourceHasCounterCondition(CounterType.SLUMBER, 1, Integer.MAX_VALUE);

    public ArixmethesSlumberingIsle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.KRAKEN);
        this.power = new MageInt(12);
        this.toughness = new MageInt(12);

        // Arixmethes, Slumbering Isle enters the battlefield tapped with five slumber counters on it.
        Ability ability = new EntersBattlefieldAbility(
                new TapSourceEffect(true), false, null,
                "{this} enters the battlefield tapped with five slumber counters on it", null
        );
        ability.addEffect(new AddCountersSourceEffect(CounterType.SLUMBER.createInstance(5)));
        this.addAbility(ability);

        // As long as Arixmethes, Slumbering Isle has a slumber counter on it, it's a land.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new ArixmethesIsLandEffect(), condition,
                "As long as {this} has a slumber counter on it, it's a land"
        )));

        // Whenever you cast a spell, you may remove a slumber counter from Arixmethes.
        this.addAbility(new SpellCastControllerTriggeredAbility(new RemoveCounterSourceEffect(CounterType.SLUMBER.createInstance(1)), true));

        // {T}: Add {G}{U}.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, new Mana(0, 1, 0, 0, 1, 0, 0, 0), new TapSourceCost()));
    }

    private ArixmethesSlumberingIsle(final ArixmethesSlumberingIsle card) {
        super(card);
    }

    @Override
    public ArixmethesSlumberingIsle copy() {
        return new ArixmethesSlumberingIsle(this);
    }
}

class ArixmethesIsLandEffect extends ContinuousEffectImpl {

    ArixmethesIsLandEffect() {
        super(Duration.WhileOnBattlefield, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Neutral);
        this.dependencyTypes.add(DependencyType.BecomeNonbasicLand);
    }

    private ArixmethesIsLandEffect(final ArixmethesIsLandEffect effect) {
        super(effect);
    }

    @Override
    public ArixmethesIsLandEffect copy() {
        return new ArixmethesIsLandEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null) {
            return false;
        }
        permanent.removeAllCardTypes(game);
        permanent.addCardType(game, CardType.LAND);
        permanent.removeAllSubTypes(game);
        return true;
    }
}
