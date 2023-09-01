package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAndIsNotBlockedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.AssignNoCombatDamageSourceEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.card.DefendingPlayerOwnsCardPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RysorianBadger extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("creature cards from defending player's graveyard");

    static {
        filter.add(DefendingPlayerOwnsCardPredicate.instance);
    }

    public RysorianBadger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.BADGER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Rysorian Badger attacks and isn't blocked, you may exile up to two target creature cards from defending player's graveyard. If you do, you gain 1 life for each card exiled this way and Rysorian Badger assigns no combat damage this turn.
        Ability ability = new AttacksAndIsNotBlockedTriggeredAbility(
                new RysorianBadgerEffect(), true
        );
        ability.addTarget(new TargetCardInGraveyard(0, 2, filter));
        this.addAbility(ability);
    }

    private RysorianBadger(final RysorianBadger card) {
        super(card);
    }

    @Override
    public RysorianBadger copy() {
        return new RysorianBadger(this);
    }
}

class RysorianBadgerEffect extends OneShotEffect {

    public RysorianBadgerEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may exile up to two target creature cards "
                + "from defending player's graveyard. If you do, "
                + "you gain 1 life for each card exiled this way "
                + "and {this} assigns no combat damage this turn.";
    }

    private RysorianBadgerEffect(final RysorianBadgerEffect effect) {
        super(effect);
    }

    @Override
    public RysorianBadgerEffect copy() {
        return new RysorianBadgerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cardsToExile = new CardsImpl();
        for (UUID cardId : this.getTargetPointer().getTargets(game, source)) {
            Card card = game.getCard(cardId);
            if (card != null) {
                cardsToExile.add(card);
            }
        }
        int cardsExiled = 0;
        player.moveCardsToExile(cardsToExile.getCards(game), source, game, false, null, null);
        for (Card card : cardsToExile.getCards(game)) {
            if (game.getState().getZone(card.getId()) == Zone.EXILED) {
                cardsExiled++;
            }
        }
        player.gainLife(cardsExiled, game, source);
        game.addEffect(new AssignNoCombatDamageSourceEffect(Duration.EndOfTurn), source);
        return true;
    }
}
