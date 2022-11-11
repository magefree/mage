package mage.cards.f;

import mage.ApprovingObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.CasualtyAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FlawlessForgery extends CardImpl {

    private static final FilterCard filter
            = new FilterInstantOrSorceryCard("instant or sorcery card from an opponent's graveyard");

    static {
        filter.add(TargetController.OPPONENT.getOwnerPredicate());
    }

    public FlawlessForgery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}{U}");

        // Casualty 3
        this.addAbility(new CasualtyAbility(3));

        // Exile target instant or sorcery card from an opponent's graveyard. Copy that card. You may cast the copy without paying its mana cost.
        this.getSpellAbility().addEffect(new FlawlessForgeryEffect());
        this.getSpellAbility().addTarget(new TargetCardInGraveyard(filter));
    }

    private FlawlessForgery(final FlawlessForgery card) {
        super(card);
    }

    @Override
    public FlawlessForgery copy() {
        return new FlawlessForgery(this);
    }
}

class FlawlessForgeryEffect extends OneShotEffect {

    FlawlessForgeryEffect() {
        super(Outcome.Benefit);
        staticText = "exile target instant or sorcery card from an opponent's graveyard. " +
                "Copy that card. You may cast the copy without paying its mana cost";
    }

    private FlawlessForgeryEffect(final FlawlessForgeryEffect effect) {
        super(effect);
    }

    @Override
    public FlawlessForgeryEffect copy() {
        return new FlawlessForgeryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (player == null || card == null) {
            return false;
        }
        player.moveCards(card, Zone.EXILED, source, game);
        Card cardCopy = game.copyCard(card, source, source.getControllerId());
        if (!player.chooseUse(outcome, "Cast copy of " +
                card.getName() + " without paying its mana cost?", source, game)) {
            return true;
        }
        game.getState().setValue("PlayFromNotOwnHandZone" + cardCopy.getId(), Boolean.TRUE);
        player.cast(
                player.chooseAbilityForCast(cardCopy, game, true),
                game, true, new ApprovingObject(source, game)
        );
        game.getState().setValue("PlayFromNotOwnHandZone" + cardCopy.getId(), null);
        return true;
    }
}
