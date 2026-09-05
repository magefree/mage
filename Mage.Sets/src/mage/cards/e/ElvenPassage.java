package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.BeholdCost;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author miesma
 */
public final class ElvenPassage extends CardImpl {

    public ElvenPassage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}, Pay 1 life, Sacrifice this land:
        // Search your library for a basic land card, put it onto the battlefield tapped, then shuffle.
        // You may behold an Elf. If you do, untap that land.
        Ability ability = new SimpleActivatedAbility(new ElvenPassageSearchForLandEffect(), new TapSourceCost());
        ability.addCost(new PayLifeCost(1));
        ability.addCost(new SacrificeSourceCost());
        ability.addEffect(new ElvenPassageUntapLandEffect());
        this.addAbility(ability);
    }

    private ElvenPassage(final ElvenPassage card) {
        super(card);
    }

    @Override
    public ElvenPassage copy() {
        return new ElvenPassage(this);
    }
}

class ElvenPassageSearchForLandEffect extends OneShotEffect {

    ElvenPassageSearchForLandEffect() {
        super(Outcome.PutCardInPlay);
        staticText = "Search your library for a basic land card, put it onto the battlefield tapped, then shuffle";
    }

    private ElvenPassageSearchForLandEffect(final ElvenPassageSearchForLandEffect effect) {
        super(effect);
    }

    @Override
    public ElvenPassageSearchForLandEffect copy() {
        return new ElvenPassageSearchForLandEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCardInLibrary target = new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND_A);
        if (!player.searchLibrary(target, source, game)) {
            return false;
        }
        player.shuffleLibrary(source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card == null) {
            return false;
        }
        if (!player.moveCards(card, Zone.BATTLEFIELD, source, game, true, false, false, null)) {
            return false;
        }
        for (Effect effect : source.getEffects()) {
            if (effect instanceof ElvenPassageUntapLandEffect) {
                effect.setTargetPointer(new FixedTarget(card, game));
                return true;
            }
        }

        return true;
    }
}

// Need to be split across two effects so that other cards (e.g. Amulet of Vigor)
// see the land enterring tapped (before this part of the effect untaps it).
class ElvenPassageUntapLandEffect extends OneShotEffect {

    ElvenPassageUntapLandEffect() {
        super(Outcome.Untap);
        staticText = "You may behold an Elf. If you do, untap that land.";
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card targetLandCard = game.getCard(getTargetPointer().getFirst(game, source));
        if (targetLandCard == null) {
            return false;
        }
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        //You may behold an Elf. If you do, untap that land.
        BeholdType beholdType = BeholdType.getBeholdType(SubType.ELF);
        Cost beholdCost = new BeholdCost(beholdType, 1);

        if (beholdCost.canPay(source, source, source.getControllerId(), game)
            && player.chooseUse(this.outcome, "Behold an Elf?", source, game)) {
                boolean paid = beholdCost.pay(source, game, source, source.getControllerId(), true);
                if (!paid) return false;
        } else {
            return true;
        }

        Permanent land = game.getPermanent(targetLandCard.getId());
        if (land == null) {
            return false;
        }
        return land.untap(game);
    }

    private ElvenPassageUntapLandEffect(final ElvenPassageUntapLandEffect effect) {
        super(effect);
    }

    @Override
    public ElvenPassageUntapLandEffect copy() {
        return new ElvenPassageUntapLandEffect(this);
    }
}
