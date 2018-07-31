
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author LevelX2
 */
public final class LoamingShaman extends CardImpl {

    public LoamingShaman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.subtype.add(SubType.CENTAUR);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Loaming Shaman enters the battlefield, target player shuffles any number of target cards from their graveyard into their library.
        Ability ability = new EntersBattlefieldTriggeredAbility(new LoamingShamanEffect(), false);
        ability.addTarget(new TargetPlayer());
        ability.addTarget(new LoamingShamanTargetCardsInGraveyard(0, Integer.MAX_VALUE, new FilterCard("cards in target player's graveyard")));
        this.addAbility(ability);
    }

    public LoamingShaman(final LoamingShaman card) {
        super(card);
    }

    @Override
    public LoamingShaman copy() {
        return new LoamingShaman(this);
    }
}

class LoamingShamanEffect extends OneShotEffect {

    public LoamingShamanEffect() {
        super(Outcome.Benefit);
        this.staticText = "target player shuffles any number of target cards from their graveyard into their library";
    }

    public LoamingShamanEffect(final LoamingShamanEffect effect) {
        super(effect);
    }

    @Override
    public LoamingShamanEffect copy() {
        return new LoamingShamanEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        if (targetPlayer != null) {
            Cards cards = new CardsImpl(source.getTargets().get(1).getTargets());
            targetPlayer.moveCards(cards, Zone.LIBRARY, source, game);
            targetPlayer.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }
}

class LoamingShamanTargetCardsInGraveyard extends TargetCardInGraveyard {

    public LoamingShamanTargetCardsInGraveyard(int minNumTargets, int maxNumTargets, FilterCard filter) {
        super(minNumTargets, maxNumTargets, filter);
    }

    public LoamingShamanTargetCardsInGraveyard(final LoamingShamanTargetCardsInGraveyard target) {
        super(target);
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        UUID targetPlayerId = source.getFirstTarget();
        UUID firstTarget = this.getFirstTarget();
        Card targetCard = game.getCard(id);
        if (firstTarget != null) {
            Card card = game.getCard(firstTarget);
            if (card == null || targetCard == null
                    || !card.isOwnedBy(targetCard.getOwnerId())) {
                return false;
            }
        } else if (targetCard == null || !targetCard.isOwnedBy(targetPlayerId)) {
            return false;
        }
        return super.canTarget(id, source, game);
    }

    @Override
    public LoamingShamanTargetCardsInGraveyard copy() {
        return new LoamingShamanTargetCardsInGraveyard(this);
    }
}
