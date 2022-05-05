package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterLandCard;
import mage.game.Game;
import mage.game.command.emblems.LilianaOfTheDarkRealmsEmblem;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author North
 */
public final class LilianaOfTheDarkRealms extends CardImpl {

    private static final FilterLandCard filter = new FilterLandCard("Swamp card");

    static {
        filter.add(SubType.SWAMP.getPredicate());
    }

    public LilianaOfTheDarkRealms(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{B}{B}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.LILIANA);

        this.setStartingLoyalty(3);

        // +1: Search your library for a Swamp card, reveal it, and put it into your hand. Then shuffle your library.
        this.addAbility(new LoyaltyAbility(new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true), 1));

        // -3: Target creature gets +X/+X or -X/-X until end of turn, where X is the number of Swamps you control.
        Ability ability = new LoyaltyAbility(new LilianaOfTheDarkRealmsEffect(), -3);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // -6: You get an emblem with "Swamps you control have '{tap}: Add {B}{B}{B}{B}.'"
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new LilianaOfTheDarkRealmsEmblem()), -6));
    }

    private LilianaOfTheDarkRealms(final LilianaOfTheDarkRealms card) {
        super(card);
    }

    @Override
    public LilianaOfTheDarkRealms copy() {
        return new LilianaOfTheDarkRealms(this);
    }
}

class LilianaOfTheDarkRealmsEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.SWAMP);

    LilianaOfTheDarkRealmsEffect() {
        super(Outcome.Neutral);
        staticText = "Target creature gets +X/+X or -X/-X until end of turn, " +
                "where X is the number of Swamps you control.";
    }

    private LilianaOfTheDarkRealmsEffect(final LilianaOfTheDarkRealmsEffect effect) {
        super(effect);
    }

    @Override
    public LilianaOfTheDarkRealmsEffect copy() {
        return new LilianaOfTheDarkRealmsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int swamps = game.getBattlefield().count(filter, source.getControllerId(), source, game);
        if (swamps < 1) {
            return false;
        }
        String plusMessage = "+" + swamps + "/+" + swamps;
        String minusMessage = "-" + swamps + "/-" + swamps;
        if (!player.chooseUse(
                outcome, "Give " + plusMessage + " or " + minusMessage + "?",
                null, plusMessage, minusMessage, source, game
        )) {
            swamps *= -1;
        }
        game.addEffect(new BoostTargetEffect(swamps, swamps, Duration.EndOfTurn), source);
        return true;
    }
}
