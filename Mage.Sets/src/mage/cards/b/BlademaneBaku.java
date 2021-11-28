 

package mage.cards.b;

 import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.RemoveVariableCountersSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
 import mage.cards.CardSetInfo;
 import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;

/**
 * @author LevelX2
 */
public final class BlademaneBaku extends CardImpl {

    public BlademaneBaku(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        
        // Whenever you cast a Spirit or Arcane spell, you may put a ki counter on Skullmane Baku.
        this.addAbility(new SpellCastControllerTriggeredAbility(new AddCountersSourceEffect(CounterType.KI.createInstance()), StaticFilters.FILTER_SPIRIT_OR_ARCANE_CARD, true));

        // {1}, Remove X ki counters from Blademane Baku: For each counter removed, Blademane Baku gets +2/+0 until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BlademaneBakuBoostEffect(), new GenericManaCost(1));
        ability.addCost(new RemoveVariableCountersSourceCost(CounterType.KI.createInstance(1)));
        this.addAbility(ability);
    }

    private BlademaneBaku(final BlademaneBaku card) {
        super(card);
    }

    @Override
    public BlademaneBaku copy() {
        return new BlademaneBaku(this);
    }
    
    static class BlademaneBakuBoostEffect extends OneShotEffect {

        public BlademaneBakuBoostEffect() {
            super(Outcome.UnboostCreature);
            staticText = "For each counter removed, {this} gets +2/+0 until end of turn";
        }

        public BlademaneBakuBoostEffect(BlademaneBakuBoostEffect effect) {
            super(effect);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            int numberToBoost = 0;
            for (Cost cost : source.getCosts()) {
                if (cost instanceof RemoveVariableCountersSourceCost) {
                    numberToBoost = ((RemoveVariableCountersSourceCost)cost).getAmount() * 2;
                }
            }
            if (numberToBoost >= 0) {
                game.addEffect(new BoostSourceEffect(numberToBoost, 0, Duration.EndOfTurn), source);
                return true;
            }
            return false;
        }

        @Override
        public BlademaneBakuBoostEffect copy() {
            return new BlademaneBakuBoostEffect(this);
        }

    }
}