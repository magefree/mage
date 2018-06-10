
package mage.cards.b;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import static mage.filter.StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author Topher
 */
public final class BurntOffering extends CardImpl {

    public BurntOffering(UUID ownerID, CardSetInfo setInfo) {
        super(ownerID, setInfo, new CardType[]{CardType.INSTANT}, "{B}");

        //As an additional cost to cast Burnt Offering, sacrifice a creature.
        this.getSpellAbility().addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(FILTER_CONTROLLED_CREATURE_SHORT_TEXT)));
        //Add an amount of {B} and/or {R} equal to the sacrificed creature's converted mana cost.
        this.getSpellAbility().addEffect(new BurntOfferingEffect());
    }

    public BurntOffering(final BurntOffering card) {
        super(card);
    }

    @Override
    public BurntOffering copy() {
        return new BurntOffering(this);
    }
}

class BurntOfferingEffect extends OneShotEffect {

    public BurntOfferingEffect() {
        super(Outcome.PutManaInPool);
        this.staticText = "Add X mana in any combination of {B} and/or {R},"
                + " where X is the sacrificed creature's converted mana cost";
    }

    public BurntOfferingEffect(final BurntOfferingEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            Choice manaChoice = new ChoiceImpl();
            Set<String> choices = new LinkedHashSet<>();
            choices.add("Red");
            choices.add("Black");
            manaChoice.setChoices(choices);
            manaChoice.setMessage("Select color of mana to add");

            int xValue = getCost(source);

            for (int i = 0; i < xValue; i++) {
                Mana mana = new Mana();
                if (!player.choose(Outcome.Benefit, manaChoice, game)) {
                    return false;
                }
                if (manaChoice.getChoice() == null) {  //Can happen if player leaves game
                    return false;
                }
                switch (manaChoice.getChoice()) {
                    case "Red":
                        mana.increaseRed();
                        break;
                    case "Black":
                        mana.increaseBlack();
                        break;
                }
                player.getManaPool().addMana(mana, game, source);
            }
            return true;
        }
        return false;
    }

    @Override
    public Effect copy() {
        return new BurntOfferingEffect(this);
    }

    /**
     * Helper method to determine the CMC of the sacrificed creature.
     *
     * @param sourceAbility
     * @return
     */
    private int getCost(Ability sourceAbility) {
        for (Cost cost : sourceAbility.getCosts()) {
            if (cost instanceof SacrificeTargetCost) {
                SacrificeTargetCost sacrificeCost = (SacrificeTargetCost) cost;
                int totalCMC = 0;
                for (Permanent permanent : sacrificeCost.getPermanents()) {
                    totalCMC += permanent.getConvertedManaCost();
                }
                return totalCMC;
            }
        }
        return 0;
    }
}
