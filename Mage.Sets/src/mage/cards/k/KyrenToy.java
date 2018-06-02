
package mage.cards.k;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.RemoveVariableCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.mana.BasicManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class KyrenToy extends CardImpl {

    public KyrenToy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {1}, {T}: Put a charge counter on Kyren Toy.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.CHARGE.createInstance(1)), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // {T}, Remove X charge counters from Kyren Toy: Add X mana of {C}, and then add {C}.
        ability = new KyrenToyManaAbility();
        ability.addCost(new RemoveVariableCountersSourceCost(CounterType.CHARGE.createInstance(1)));
        this.addAbility(ability);
    }

    public KyrenToy(final KyrenToy card) {
        super(card);
    }

    @Override
    public KyrenToy copy() {
        return new KyrenToy(this);
    }

    private class KyrenToyManaAbility extends BasicManaAbility {

        KyrenToyManaAbility() {
            super(new KyrenToyManaEffect());
        }

        KyrenToyManaAbility(final KyrenToyManaAbility ability) {
            super(ability);
        }

        @Override
        public KyrenToyManaAbility copy() {
            return new KyrenToyManaAbility(this);
        }
    }

    private static class KyrenToyManaEffect extends ManaEffect {

        KyrenToyManaEffect() {
            super();
            staticText = "Add an amount of {C} equal to X plus one";
        }

        KyrenToyManaEffect(final KyrenToyManaEffect effect) {
            super(effect);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null) {
                checkToFirePossibleEvents(getMana(game, source), game, source);
                controller.getManaPool().addMana(getMana(game, source), game, source);
                return true;
            }
            return false;
        }

        @Override
        public Mana produceMana(boolean netMana, Game game, Ability source) {
            if (netMana) {
                Permanent sourceObject = game.getPermanent(source.getSourceId());
                if (sourceObject != null) {
                    return new Mana(0, 0, 0, 0, 0, 0, 0, sourceObject.getCounters(game).getCount(CounterType.CHARGE) + 1);
                }
                return null;
            }
            Player player = game.getPlayer(source.getControllerId());
            if (player != null) {
                int numberOfMana = 0;
                for (Cost cost : source.getCosts()) {
                    if (cost instanceof RemoveVariableCountersSourceCost) {
                        numberOfMana = ((RemoveVariableCountersSourceCost) cost).getAmount();
                    }
                }
                return new Mana(0, 0, 0, 0, 0, 0, 0, numberOfMana + 1);
            }
            return null;
        }

        @Override
        public KyrenToyManaEffect copy() {
            return new KyrenToyManaEffect(this);
        }
    }
}
