package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.abilities.keyword.IslandwalkAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class ThadaAdelAcquisitor extends CardImpl {

    public ThadaAdelAcquisitor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Islandwalk
        this.addAbility(new IslandwalkAbility());

        // Whenever Thada Adel, Acquisitor deals combat damage to a player, search that player's library for an artifact card and exile it. Then that player shuffles their library. Until end of turn, you may play that card.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new ThadaAdelAcquisitorEffect(), false, true
        ));
    }

    private ThadaAdelAcquisitor(final ThadaAdelAcquisitor card) {
        super(card);
    }

    @Override
    public ThadaAdelAcquisitor copy() {
        return new ThadaAdelAcquisitor(this);
    }
}

class ThadaAdelAcquisitorEffect extends OneShotEffect {

    ThadaAdelAcquisitorEffect() {
        super(Outcome.Exile);
        staticText = "search that player's library for an artifact card and exile it. Then that player shuffles. Until end of turn, you may play that card";
    }

    ThadaAdelAcquisitorEffect(final ThadaAdelAcquisitorEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player damagedPlayer = game.getPlayer(targetPointer.getFirst(game, source));
        if (controller == null || damagedPlayer == null) {
            return false;
        }
        TargetCardInLibrary target = new TargetCardInLibrary(StaticFilters.FILTER_CARD_ARTIFACT);
        controller.searchLibrary(target, source, game, damagedPlayer.getId());
        Card card = damagedPlayer.getLibrary().getCard(target.getFirstTarget(), game);
        PlayFromNotOwnHandZoneTargetEffect.exileAndPlayFromExile(
                game, source, card, TargetController.YOU, Duration.EndOfTurn,
                false, false, false
        );
        damagedPlayer.shuffleLibrary(source, game);
        return true;
    }

    @Override
    public ThadaAdelAcquisitorEffect copy() {
        return new ThadaAdelAcquisitorEffect(this);
    }
}
