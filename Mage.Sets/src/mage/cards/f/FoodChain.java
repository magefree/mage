package mage.cards.f;

import mage.ConditionalMana;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.ExileTargetCost;
import mage.abilities.effects.mana.ManaEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.abilities.mana.conditional.CreatureCastConditionalMana;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author emerald000
 */
public final class FoodChain extends CardImpl {

    public FoodChain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        // Exile a creature you control: Add X mana of any one color, where X is the exiled creature's converted mana cost plus one. Spend this mana only to cast creature spells.
        Ability ability = new SimpleManaAbility(Zone.BATTLEFIELD, new FoodChainManaEffect(),
                new ExileTargetCost(new TargetControlledCreaturePermanent(1, 1, StaticFilters.FILTER_CONTROLLED_A_CREATURE, true)));
        this.addAbility(ability);
    }

    private FoodChain(final FoodChain card) {
        super(card);
    }

    @Override
    public FoodChain copy() {
        return new FoodChain(this);
    }
}

class FoodChainManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new CreatureCastConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast creature spells";
    }
}

class FoodChainManaEffect extends ManaEffect {

    ConditionalManaBuilder manaBuilder = new FoodChainManaBuilder();

    FoodChainManaEffect() {
        this.staticText = "Add X mana of any one color, where X is 1 plus the exiled creature's mana value. Spend this mana only to cast creature spells";
    }

    FoodChainManaEffect(final FoodChainManaEffect effect) {
        super(effect);
    }

    @Override
    public FoodChainManaEffect copy() {
        return new FoodChainManaEffect(this);
    }

    @Override
    public List<Mana> getNetMana(Game game, Ability source) {
        List<Mana> netMana = new ArrayList<>();
        if (game != null) {
            int cmc = -1;
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(source.getControllerId())) {
                if (permanent.isCreature(game)) {
                    cmc = Math.max(cmc, permanent.getManaCost().manaValue());
                }
            }
            if (cmc != -1) {
                netMana.add(manaBuilder.setMana(Mana.BlackMana(cmc + 1), source, game).build());
                netMana.add(manaBuilder.setMana(Mana.BlueMana(cmc + 1), source, game).build());
                netMana.add(manaBuilder.setMana(Mana.RedMana(cmc + 1), source, game).build());
                netMana.add(manaBuilder.setMana(Mana.GreenMana(cmc + 1), source, game).build());
                netMana.add(manaBuilder.setMana(Mana.WhiteMana(cmc + 1), source, game).build());
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
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int manaCostExiled = 0;
            for (Cost cost : source.getCosts()) {
                if (cost.isPaid() && cost instanceof ExileTargetCost) {
                    for (Card card : ((ExileTargetCost) cost).getPermanents()) {
                        manaCostExiled += card.getManaValue();
                    }
                }
            }
            ChoiceColor choice = new ChoiceColor();
            if (!controller.choose(Outcome.PutManaInPool, choice, game)) {
                return mana;
            }
            Mana chosen = choice.getMana(manaCostExiled + 1);
            return manaBuilder.setMana(chosen, source, game).build();
        }
        return mana;
    }

}
