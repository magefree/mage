
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.RemoveVariableCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 * @author LevelX2
 */
public final class SkullmaneBaku extends CardImpl {

    public SkullmaneBaku(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever you cast a Spirit or Arcane spell, you may put a ki counter on Skullmane Baku.
        this.addAbility(new SpellCastControllerTriggeredAbility(new AddCountersSourceEffect(CounterType.KI.createInstance()), StaticFilters.FILTER_SPIRIT_OR_ARCANE_CARD, true));

        // {1}, {T}, Remove X ki counters from Skullmane Baku: Target creature gets -X/-X until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new SkullmaneBakuUnboostEffect(), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addCost(new RemoveVariableCountersSourceCost(CounterType.KI.createInstance(1)));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private SkullmaneBaku(final SkullmaneBaku card) {
        super(card);
    }

    @Override
    public SkullmaneBaku copy() {
        return new SkullmaneBaku(this);
    }

    static class SkullmaneBakuUnboostEffect extends OneShotEffect {

        public SkullmaneBakuUnboostEffect() {
            super(Outcome.UnboostCreature);
            staticText = "Target creature gets -X/-X until end of turn";
        }

        public SkullmaneBakuUnboostEffect(SkullmaneBakuUnboostEffect effect) {
            super(effect);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            int numberToUnboost = 0;
            for (Cost cost : source.getCosts()) {
                if (cost instanceof RemoveVariableCountersSourceCost) {
                    numberToUnboost = ((RemoveVariableCountersSourceCost) cost).getAmount() * -1;
                }
            }
            Permanent creature = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (creature != null && numberToUnboost != 0) {
                ContinuousEffect effect = new BoostTargetEffect(numberToUnboost, numberToUnboost, Duration.EndOfTurn);
                effect.setTargetPointer(new FixedTarget(creature, game));
                game.addEffect(effect, source);
            }
            return true;
        }

        @Override
        public SkullmaneBakuUnboostEffect copy() {
            return new SkullmaneBakuUnboostEffect(this);
        }

    }
}
