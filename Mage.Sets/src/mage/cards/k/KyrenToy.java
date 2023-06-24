package mage.cards.k;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.RemoveVariableCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.mana.ManaEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author LevelX2
 */
public final class KyrenToy extends CardImpl {

    public KyrenToy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {1}, {T}: Put a charge counter on Kyren Toy.
        Ability ability = new SimpleActivatedAbility(new AddCountersSourceEffect(CounterType.CHARGE.createInstance(1)), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // {T}, Remove X charge counters from Kyren Toy: Add X mana of {C}, and then add {C}.
        ability = new SimpleManaAbility(Zone.BATTLEFIELD, new KyrenToyManaEffect(), new TapSourceCost());
        ability.addCost(new RemoveVariableCountersSourceCost(CounterType.CHARGE));
        this.addAbility(ability);
    }

    private KyrenToy(final KyrenToy card) {
        super(card);
    }

    @Override
    public KyrenToy copy() {
        return new KyrenToy(this);
    }

    private static class KyrenToyManaEffect extends ManaEffect {

        private KyrenToyManaEffect() {
            super();
            staticText = "Add an amount of {C} equal to X plus one";
        }

        private KyrenToyManaEffect(final KyrenToyManaEffect effect) {
            super(effect);
        }

        @Override
        public List<Mana> getNetMana(Game game, Ability source) {
            List<Mana> netMana = new ArrayList<>();
            if (game != null) {
                Permanent sourceObject = game.getPermanent(source.getSourceId());
                if (sourceObject != null) {
                    netMana.add(Mana.ColorlessMana(sourceObject.getCounters(game).getCount(CounterType.CHARGE) + 1));
                    return netMana;
                }
            }
            return netMana;
        }

        @Override
        public Mana produceMana(Game game, Ability source) {
            Mana mana = new Mana();
            if (game == null) {
                return mana;
            }
            Player player = game.getPlayer(source.getControllerId());
            if (player == null) {
                return mana;
            }
            int numberOfMana = 0;
            for (Cost cost : source.getCosts()) {
                if (cost instanceof RemoveVariableCountersSourceCost) {
                    numberOfMana = ((RemoveVariableCountersSourceCost) cost).getAmount();
                }
            }
            return new Mana(Mana.ColorlessMana(numberOfMana + 1));
        }

        @Override
        public KyrenToyManaEffect copy() {
            return new KyrenToyManaEffect(this);
        }
    }
}
