
package mage.cards.c;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author TheElk801
 */
public final class CreditVoucher extends CardImpl {

    public CreditVoucher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // {2}, {tap}, Sacrifice Credit Voucher: Shuffle any number of cards from your hand into your library, then draw that many cards.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreditVoucherEffect(), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private CreditVoucher(final CreditVoucher card) {
        super(card);
    }

    @Override
    public CreditVoucher copy() {
        return new CreditVoucher(this);
    }
}

class CreditVoucherEffect extends OneShotEffect {

    CreditVoucherEffect() {
        super(Outcome.Neutral);
        this.staticText = "Shuffle any number of cards from your hand into your library, then draw that many cards";
    }

    CreditVoucherEffect(final CreditVoucherEffect effect) {
        super(effect);
    }

    @Override
    public CreditVoucherEffect copy() {
        return new CreditVoucherEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (controller != null && sourceObject != null) {
            FilterCard filter = new FilterCard("card in your hand to shuffle away");
            TargetCardInHand target = new TargetCardInHand(0, controller.getHand().size(), filter);
            target.setRequired(false);
            int amountShuffled = 0;
            if (target.canChoose(source.getControllerId(), source, game) && target.choose(Outcome.Neutral, source.getControllerId(), source.getSourceId(), source, game)) {
                if (!target.getTargets().isEmpty()) {
                    amountShuffled = target.getTargets().size();
                    controller.moveCards(new CardsImpl(target.getTargets()), Zone.LIBRARY, source, game);
                }
            }
            controller.shuffleLibrary(source, game);
            if (amountShuffled > 0) {
                controller.drawCards(amountShuffled, source, game);
            }
            return true;
        }
        return false;
    }
}
