package mage.cards.l;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.common.search.SearchTargetGraveyardHandLibraryForCardNameAndExileEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPlayer;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class Lobotomy extends CardImpl {

    public Lobotomy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}{B}");

        // Target player reveals their hand, then you choose a card other than a basic land card from it. Search that player's graveyard, hand, and library for all cards with the same name as the chosen card and exile them. Then that player shuffles their library.
        this.getSpellAbility().addEffect(new LobotomyEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private Lobotomy(final Lobotomy card) {
        super(card);
    }

    @Override
    public Lobotomy copy() {
        return new Lobotomy(this);
    }
}

class LobotomyEffect extends SearchTargetGraveyardHandLibraryForCardNameAndExileEffect {

    private static final FilterCard filter = new FilterCard("card other than a basic land card");

    static {
        filter.add(Predicates.not(Predicates.and(CardType.LAND.getPredicate(), SuperType.BASIC.getPredicate())));
    }

    public LobotomyEffect() {
        super(false, "that player's", "all cards with the same name as the chosen card");
        staticText = "Target player reveals their hand, then you choose a card other than a basic land card from it. Search that player's graveyard, hand, and library for all cards with the same name as the chosen card and exile them. Then that player shuffles";
    }

    private LobotomyEffect(final LobotomyEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (targetPlayer != null && sourceObject != null && controller != null) {

            // reveal hand of target player
            targetPlayer.revealCards(sourceObject.getIdName(), targetPlayer.getHand(), game);

            // You choose card other than a basic land card
            TargetCard target = new TargetCard(Zone.HAND, filter);
            target.withNotTarget(true);
            Card chosenCard = null;
            if (controller.chooseTarget(Outcome.Benefit, targetPlayer.getHand(), target, source, game)) {
                chosenCard = game.getCard(target.getFirstTarget());
            }

            return applySearchAndExile(game, source, CardUtil.getCardNameForSameNameSearch(chosenCard), getTargetPointer().getFirst(game, source));
        }
        return false;
    }

    @Override
    public LobotomyEffect copy() {
        return new LobotomyEffect(this);
    }
}
