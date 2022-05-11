package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.ManaUtil;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class CutTheTethers extends CardImpl {

    public CutTheTethers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}{U}");

        // For each Spirit, return it to its owner's hand unless that player pays {3}.
        this.getSpellAbility().addEffect(new CutTheTethersEffect());
    }

    private CutTheTethers(final CutTheTethers card) {
        super(card);
    }

    @Override
    public CutTheTethers copy() {
        return new CutTheTethers(this);
    }
}

class CutTheTethersEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent(SubType.SPIRIT, "");

    CutTheTethersEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "For each Spirit, return it to its owner's hand unless that player pays {3}";
    }

    private CutTheTethersEffect(final CutTheTethersEffect effect) {
        super(effect);
    }

    @Override
    public CutTheTethersEffect copy() {
        return new CutTheTethersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards toHand = new CardsImpl();
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                filter, source.getControllerId(), source, game
        )) {
            Player player = game.getPlayer(permanent.getOwnerId());
            if (player == null) {
                continue;
            }
            boolean paid = false;
            if (player.chooseUse(Outcome.Benefit, "Pay {3} to keep " + permanent.getIdName() + " on the battlefield?", source, game)) {
                Cost cost = ManaUtil.createManaCost(3, false);
                paid = cost.pay(source, game, source, permanent.getControllerId(), false, null);
            }
            if (!paid) {
                toHand.add(permanent);
            }
        }
        controller.moveCards(toHand, Zone.HAND, source, game);
        return true;
    }
}
