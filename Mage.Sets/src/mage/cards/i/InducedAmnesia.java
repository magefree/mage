package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.PutIntoGraveFromBattlefieldSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class InducedAmnesia extends CardImpl {

    public InducedAmnesia(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        // When Induced Amnesia enters the battlefield, target player exiles all the cards in their hand face down, then draws that many cards.
        Ability ability = new EntersBattlefieldTriggeredAbility(new InducedAmnesiaExileEffect(), false);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // When Induced Amnesia is put into a graveyard from the battlefield, return the exiled cards to their owner's hand.
        this.addAbility(new PutIntoGraveFromBattlefieldSourceTriggeredAbility(new InducedAmnesiaReturnEffect()));
    }

    private InducedAmnesia(final InducedAmnesia card) {
        super(card);
    }

    @Override
    public InducedAmnesia copy() {
        return new InducedAmnesia(this);
    }
}

class InducedAmnesiaExileEffect extends OneShotEffect {

    InducedAmnesiaExileEffect() {
        super(Outcome.Detriment);
        this.staticText = "target player exiles all the cards in their hand face down, then draws that many cards";
    }

    private InducedAmnesiaExileEffect(final InducedAmnesiaExileEffect effect) {
        super(effect);
    }

    @Override
    public InducedAmnesiaExileEffect copy() {
        return new InducedAmnesiaExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (targetPlayer == null) {
            return false;
        }
        int numberOfCards = targetPlayer.getHand().size();
        if (numberOfCards < 1) {
            return false;
        }
        Cards cards = new CardsImpl(targetPlayer.getHand());
        targetPlayer.moveCardsToExile(
                cards.getCards(game), source, game, false,
                CardUtil.getExileZoneId(game, source), CardUtil.getSourceName(game, source)
        );
        cards.getCards(game)
                .stream()
                .filter(card -> game.getState().getZone(card.getId()) == Zone.EXILED)
                .forEach(card -> card.setFaceDown(true, game));
        targetPlayer.drawCards(numberOfCards, source, game);
        return true;
    }
}

class InducedAmnesiaReturnEffect extends OneShotEffect {

    InducedAmnesiaReturnEffect() {
        super(Outcome.Benefit);
        this.staticText = "return the exiled cards to their owner's hand";
    }

    private InducedAmnesiaReturnEffect(final InducedAmnesiaReturnEffect effect) {
        super(effect);
    }

    @Override
    public InducedAmnesiaReturnEffect copy() {
        return new InducedAmnesiaReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        ExileZone exileZone = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source));
        return controller != null
                && exileZone != null
                && !exileZone.isEmpty()
                && controller.moveCards(exileZone, Zone.HAND, source, game);
    }
}
