package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInExile;
import mage.util.CardUtil;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AuthorOfShadows extends CardImpl {

    public AuthorOfShadows(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.subtype.add(SubType.SHADE);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Author of Shadows enters the battlefield, exile all cards from all opponents' graveyards. Choose a nonland card exiled this way. You may cast that card for as long as it remains exiled, and you may spend mana as though it were mana of any color to cast that spell.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new AuthorOfShadowsEffect()));
    }

    private AuthorOfShadows(final AuthorOfShadows card) {
        super(card);
    }

    @Override
    public AuthorOfShadows copy() {
        return new AuthorOfShadows(this);
    }
}

class AuthorOfShadowsEffect extends OneShotEffect {

    AuthorOfShadowsEffect() {
        super(Outcome.Benefit);
        staticText = "exile all opponents' graveyards. Choose a nonland card exiled this way. " +
                "You may cast that card for as long as it remains exiled, and you may spend mana " +
                "as though it were mana of any color to cast that spell";
    }

    private AuthorOfShadowsEffect(final AuthorOfShadowsEffect effect) {
        super(effect);
    }

    @Override
    public AuthorOfShadowsEffect copy() {
        return new AuthorOfShadowsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        game.getOpponents(source.getControllerId())
                .stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .map(Player::getGraveyard)
                .forEach(cards::addAll);
        cards.removeIf(Objects::isNull);
        if (cards.isEmpty()) {
            return false;
        }

        controller.moveCards(cards, Zone.EXILED, source, game);
        cards.retainZone(Zone.EXILED, game);
        if (cards.isEmpty()) {
            return false;
        }

        TargetCard target = new TargetCardInExile(StaticFilters.FILTER_CARD_A_NON_LAND);
        target.setNotTarget(true);
        controller.choose(outcome, cards, target, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card == null) {
            return true;
        }

        // use same player's zone for all Author of Shadows instances
        String exileZoneName = controller.getName() + " - Author of Shadows - cast with any color";
        UUID exileZoneId = CardUtil.getExileZoneId(exileZoneName, game);
        ExileZone exileZone = game.getExile().createZone(exileZoneId, exileZoneName);
        game.getExile().moveToAnotherZone(card, game, exileZone);

        CardUtil.makeCardPlayable(game, source, card, Duration.Custom, true);
        return true;
    }
}
