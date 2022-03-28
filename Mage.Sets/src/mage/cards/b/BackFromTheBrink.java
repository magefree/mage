
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author BetaSteward
 */
public final class BackFromTheBrink extends CardImpl {

    public BackFromTheBrink(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{U}{U}");

        // Exile a creature card from your graveyard and pay its mana cost: Create a token that's a copy of that card. Activate this ability only any time you could cast a sorcery.
        Effect effect = new CreateTokenCopyTargetEffect();
        effect.setText("create a token that's a copy of that card");
        this.addAbility(new ActivateAsSorceryActivatedAbility(Zone.BATTLEFIELD, effect, new BackFromTheBrinkCost()));

    }

    private BackFromTheBrink(final BackFromTheBrink card) {
        super(card);
    }

    @Override
    public BackFromTheBrink copy() {
        return new BackFromTheBrink(this);
    }
}

class BackFromTheBrinkCost extends CostImpl {

    public BackFromTheBrinkCost() {
        Target target = new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD);
        target.setNotTarget(true);
        this.addTarget(target);
        this.text = "Exile a creature card from your graveyard and pay its mana cost";
    }

    public BackFromTheBrinkCost(final BackFromTheBrinkCost cost) {
        super(cost);
    }

    @Override
    public BackFromTheBrinkCost copy() {
        return new BackFromTheBrinkCost(this);
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return targets.canChoose(controllerId, source, game);
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        if (targets.choose(Outcome.Exile, controllerId, source.getSourceId(), source, game)) {
            Player controller = game.getPlayer(controllerId);
            if (controller != null) {
                Card card = controller.getGraveyard().get(targets.getFirstTarget(), game);
                if (card != null && controller.moveCards(card, Zone.EXILED, ability, game)) {
                    ability.getEffects().get(0).setTargetPointer(new FixedTarget(card.getId(), game.getState().getZoneChangeCounter(card.getId())));
                    paid = card.getManaCost().pay(ability, game, source, controllerId, noMana);
                }
            }
        }
        return paid;
    }

}
