
package mage.abilities.keyword;

import java.util.List;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.SpecialAction;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.mana.AlternateManaPaymentAbility;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.AbilityType;
import mage.constants.ManaType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.ManaPool;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.util.CardUtil;

/**
 * 702.65. Delve 702.65a Delve is a static ability that functions while the
 * spell with delve is on the stack. “Delve” means “For each generic mana in
 * this spell's total cost, you may exile a card from your graveyard rather than
 * pay that mana.” The delve ability isn't an additional or alternative cost and
 * applies only after the total cost of the spell with delve is determined.
 * 702.65b Multiple instances of delve on the same spell are redundant.
 *
 * The rules for delve have changed slightly since it was last in an expansion.
 * Previously, delve reduced the cost to cast a spell. Under the current rules,
 * you exile cards from your graveyard at the same time you pay the spell's
 * cost. Exiling a card this way is simply another way to pay that cost. * Delve
 * doesn't change a spell's mana cost or converted mana cost. For example, Dead
 * Drop's converted mana cost is 10 even if you exiled three cards to cast it. *
 * You can't exile cards to pay for the colored mana requirements of a spell
 * with delve. * You can't exile more cards than the generic mana requirement of
 * a spell with delve. For example, you can't exile more than nine cards from
 * your graveyard to cast Dead Drop. * Because delve isn't an alternative cost,
 * it can be used in conjunction with alternative costs.
 *
 * @author LevelX2
 *
 * TODO: Change card exiling to a way to pay mana costs, now it's maybe not
 * possible to pay costs from effects that increase the mana costs.
 */
public class DelveAbility extends SimpleStaticAbility implements AlternateManaPaymentAbility {

    public DelveAbility() {
        super(Zone.STACK, null);
        this.setRuleAtTheTop(true);
    }

    public DelveAbility(final DelveAbility ability) {
        super(ability);
    }

    @Override
    public DelveAbility copy() {
        return new DelveAbility(this);
    }

    @Override
    public String getRule() {
        return "Delve <i>(Each card you exile from your graveyard while casting this spell pays for {1}.)</i>";
    }

    @Override
    public void addSpecialAction(Ability source, Game game, ManaCost unpaid) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && !controller.getGraveyard().isEmpty()) {
            if (unpaid.getMana().getGeneric() > 0 && source.getAbilityType() == AbilityType.SPELL) {
                SpecialAction specialAction = new DelveSpecialAction();
                specialAction.setControllerId(source.getControllerId());
                specialAction.setSourceId(source.getSourceId());
                int unpaidAmount = unpaid.getMana().getGeneric();
                if (!controller.getManaPool().isAutoPayment() && unpaidAmount > 1) {
                    unpaidAmount = 1;
                }
                specialAction.addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(
                        0, Math.min(controller.getGraveyard().size(), unpaidAmount), new FilterCard(), true)));
                if (specialAction.canActivate(source.getControllerId(), game).canActivate()) {
                    game.getState().getSpecialActions().add(specialAction);
                }
            }
        }
    }
}

class DelveSpecialAction extends SpecialAction {

    public DelveSpecialAction() {
        super(Zone.ALL, true);
        this.addEffect(new DelveEffect());
    }

    public DelveSpecialAction(final DelveSpecialAction ability) {
        super(ability);
    }

    @Override
    public DelveSpecialAction copy() {
        return new DelveSpecialAction(this);
    }
}

class DelveEffect extends OneShotEffect {

    public DelveEffect() {
        super(Outcome.Benefit);
        this.staticText = "Delve (Each card you exile from your graveyard while casting this spell pays for {1}.)";
    }

    public DelveEffect(final DelveEffect effect) {
        super(effect);
    }

    @Override
    public DelveEffect copy() {
        return new DelveEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            ExileFromGraveCost exileFromGraveCost = (ExileFromGraveCost) source.getCosts().get(0);

            List<Card> exiledCards = exileFromGraveCost.getExiledCards();
            if (!exiledCards.isEmpty()) {
                Cards toDelve = new CardsImpl();
                for (Card card : exiledCards) {
                    toDelve.add(card);
                }
                ManaPool manaPool = controller.getManaPool();
                manaPool.addMana(new Mana(0, 0, 0, 0, 0, 0, 0, toDelve.size()), game, source);
                manaPool.unlockManaType(ManaType.COLORLESS);
                String keyString = CardUtil.getCardZoneString("delvedCards", source.getSourceId(), game);
                @SuppressWarnings("unchecked")
                Cards delvedCards = (Cards) game.getState().getValue(keyString);
                if (delvedCards == null) {
                    game.getState().setValue(keyString, toDelve);
                } else {
                    delvedCards.addAll(toDelve);
                }
            }
            return true;
        }
        return false;
    }
}
