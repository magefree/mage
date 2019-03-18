package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import static mage.constants.Layer.TypeChangingEffects_4;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author spjspj
 */
public final class ArixmethesSlumberingIsle extends CardImpl {

    public ArixmethesSlumberingIsle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{U}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.KRAKEN);
        this.power = new MageInt(12);
        this.toughness = new MageInt(12);

        // Arixmethes, Slumbering Isle enters the battlefield tapped with five slumber counters on it.
        this.addAbility(new EntersBattlefieldTappedAbility());
        Effect effect = new AddCountersSourceEffect(CounterType.SLUMBER.createInstance(5));
        this.addAbility(new EntersBattlefieldAbility(effect, "with five slumber counters"));

        // As long as Arixmethes, Slumbering Isle has a slumber counter on it, it's a land.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
                new ArixmethesIsLandEffect(),
                new SourceHasCounterCondition(CounterType.SLUMBER, 1, Integer.MAX_VALUE),
                "As long as {this} has a slumber counter on it, it's a land")));

        // Whenever you cast a spell, you may remove a slumber counter from Arixmethes.
        this.addAbility(new SpellCastControllerTriggeredAbility(new RemoveCounterSourceEffect(CounterType.SLUMBER.createInstance(1)), true));

        // {T}: Add {G}{U}.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, new Mana(0, 1, 1, 0, 0, 0, 0, 0), new TapSourceCost()));
    }

    public ArixmethesSlumberingIsle(final ArixmethesSlumberingIsle card) {
        super(card);
    }

    @Override
    public ArixmethesSlumberingIsle copy() {
        return new ArixmethesSlumberingIsle(this);
    }
}

class ArixmethesIsLandEffect extends ContinuousEffectImpl {

    public ArixmethesIsLandEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        this.staticText = "As long as {this} has a slumber counter on it, it's a land";
    }

    public ArixmethesIsLandEffect(final ArixmethesIsLandEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public ArixmethesIsLandEffect copy() {
        return new ArixmethesIsLandEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent permanent = game.getPermanent(source.getSourceId());

        if (permanent != null) {
            switch (layer) {
                case TypeChangingEffects_4:
                    permanent.getCardType().clear();
                    permanent.addCardType(CardType.LAND);
                    permanent.getSubtype(game).clear();
                    break;
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.TypeChangingEffects_4;
    }
}
