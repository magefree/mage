package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.util.ManaUtil;

import java.util.UUID;

/**
 * @author North
 */
public final class SilvergillAdept extends CardImpl {

    public SilvergillAdept(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // As an additional cost to cast Silvergill Adept, reveal a Merfolk card from your hand or pay {3}.
        this.getSpellAbility().addCost(new SilvergillAdeptCost());
        // When Silvergill Adept enters the battlefield, draw a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1)));
    }

    private SilvergillAdept(final SilvergillAdept card) {
        super(card);
    }

    @Override
    public SilvergillAdept copy() {
        return new SilvergillAdept(this);
    }
}

class SilvergillAdeptCost extends CostImpl {

    private static final FilterCard filter = new FilterCard("Merfolk card");
    private Cost mana = ManaUtil.createManaCost(3, false);

    static {
        filter.add(SubType.MERFOLK.getPredicate());
    }

    public SilvergillAdeptCost() {
        this.text = "reveal a Merfolk card from your hand or pay {3}";
    }

    public SilvergillAdeptCost(SilvergillAdeptCost cost) {
        super(cost);
        this.mana = cost.mana.copy();
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {

        Player player = game.getPlayer(controllerId);
        if (player == null) {
            return false;
        }

        paid = false;
        if (player.getHand().count(filter, game) > 0
                && player.chooseUse(Outcome.Benefit, "Reveal a Merfolk card? Otherwise pay {3}.", ability, game)) {
            TargetCardInHand target = new TargetCardInHand(filter);
            if (player.choose(Outcome.Benefit, target, source, game)) {
                Card card = player.getHand().get(target.getFirstTarget(), game);
                if (card != null) {
                    paid = true;
                    player.revealCards("Revealed card", new CardsImpl(card), game);
                }
            }
        } else {
            mana.clearPaid();
            if (mana.pay(ability, game, source, player.getId(), false)) {
                paid = true;
            }
        }

        return paid;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        Player player = game.getPlayer(controllerId);
        if (player != null && player.getHand().count(filter, game) > 0) {
            return true;
        }

        return mana.canPay(ability, source, controllerId, game);

    }

    @Override
    public SilvergillAdeptCost copy() {
        return new SilvergillAdeptCost(this);
    }
}
