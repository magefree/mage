package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SiegeAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInExile;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InvasionOfAlara extends CardImpl {

    public InvasionOfAlara(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.BATTLE}, "{W}{U}{B}{R}{G}");

        this.subtype.add(SubType.SIEGE);
        this.setStartingDefense(7);
        this.secondSideCardClazz = mage.cards.a.AwakenTheMaelstrom.class;

        Ability ability = new SiegeAbility();
        ability.setRuleVisible(false);
        this.addAbility(ability);

        // When Invasion of Alara enters the battlefield, exile cards from the top of your library until you exile two nonland cards with mana value 4 or less. You may cast one of those two cards without paying its mana cost. Put one of them into your hand. Then put the other cards exiled this way on the bottom of your library in a random order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new InvasionOfAlaraEffect()));
    }

    private InvasionOfAlara(final InvasionOfAlara card) {
        super(card);
    }

    @Override
    public InvasionOfAlara copy() {
        return new InvasionOfAlara(this);
    }
}

class InvasionOfAlaraEffect extends OneShotEffect {

    InvasionOfAlaraEffect() {
        super(Outcome.Benefit);
        staticText = "exile cards from the top of your library until you exile two nonland cards " +
                "with mana value 4 or less. You may cast one of those two cards without paying its mana cost. " +
                "Put one of them into your hand. Then put the other cards exiled this way on the bottom of your library in a random order";
    }

    private InvasionOfAlaraEffect(final InvasionOfAlaraEffect effect) {
        super(effect);
    }

    @Override
    public InvasionOfAlaraEffect copy() {
        return new InvasionOfAlaraEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        Cards castable = new CardsImpl();
        int count = 0;
        for (Card card : player.getLibrary().getCards(game)) {
            player.moveCards(card, Zone.EXILED, source, game);
            cards.add(card);
            if (!card.isLand(game) && card.getManaValue() <= 4) {
                castable.add(card);
                count++;
            }
            if (count >= 2) {
                break;
            }
        }
        CardUtil.castSpellWithAttributesForFree(player, source, game, castable, StaticFilters.FILTER_CARD);
        castable.retainZone(Zone.EXILED, game);
        if (castable.size() > 1) {
            TargetCard target = new TargetCardInExile(StaticFilters.FILTER_CARD);
            target.setNotTarget(true);
            player.choose(outcome, castable, target, source, game);
            player.moveCards(game.getCard(target.getFirstTarget()), Zone.HAND, source, game);
        } else {
            player.moveCards(castable, Zone.HAND, source, game);
        }
        cards.retainZone(Zone.EXILED, game);
        player.putCardsOnBottomOfLibrary(cards, game, source, false);
        return true;
    }
}
