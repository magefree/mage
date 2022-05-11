package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RootsOfWisdom extends CardImpl {

    public RootsOfWisdom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");

        // Mill three cards, then return a land card or Elf card from your graveyard to your hand. If you can't, draw a card.
        this.getSpellAbility().addEffect(new RootsOfWisdomEffect());
    }

    private RootsOfWisdom(final RootsOfWisdom card) {
        super(card);
    }

    @Override
    public RootsOfWisdom copy() {
        return new RootsOfWisdom(this);
    }
}

class RootsOfWisdomEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("land card or Elf card");

    static {
        filter.add(Predicates.or(
                CardType.LAND.getPredicate(),
                SubType.ELF.getPredicate()
        ));
    }

    RootsOfWisdomEffect() {
        super(Outcome.Benefit);
        staticText = "Mill three cards, then return a land card or Elf card " +
                "from your graveyard to your hand. If you can't, draw a card.";
    }

    private RootsOfWisdomEffect(final RootsOfWisdomEffect effect) {
        super(effect);
    }

    @Override
    public RootsOfWisdomEffect copy() {
        return new RootsOfWisdomEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        player.millCards(3, source, game);
        TargetCard targetCard = new TargetCardInYourGraveyard(filter);
        targetCard.setNotTarget(true);
        if (targetCard.canChoose(source.getControllerId(), source, game)
                && player.choose(outcome, targetCard, source, game)) {
            Card card = player.getGraveyard().get(targetCard.getFirstTarget(), game);
            if (card != null && player.moveCards(card, Zone.HAND, source, game)) {
                return true;
            }
        }
        player.drawCards(1, source, game);
        return true;
    }
}
