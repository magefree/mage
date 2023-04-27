package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.CasualtyAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AudaciousSwap extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("nonenchantment permanent");

    static {
        filter.add(Predicates.not(CardType.ENCHANTMENT.getPredicate()));
    }

    public AudaciousSwap(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{R}");

        // Casualty 2
        this.addAbility(new CasualtyAbility(2));

        // The owner of target nonenchantment permanent shuffles it into their library, then exiles the top card of their library. If it's a land card, they put it onto the battlefield. Otherwise, they may cast it without paying its mana cost.
        this.getSpellAbility().addEffect(new AudaciousSwapEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private AudaciousSwap(final AudaciousSwap card) {
        super(card);
    }

    @Override
    public AudaciousSwap copy() {
        return new AudaciousSwap(this);
    }
}

class AudaciousSwapEffect extends OneShotEffect {

    AudaciousSwapEffect() {
        super(Outcome.Benefit);
        staticText = "the owner of target nonenchantment permanent shuffles it into their library, then " +
                "exiles the top card of their library. If it's a land card, they put it onto the battlefield. " +
                "Otherwise, they may cast it without paying its mana cost";
    }

    private AudaciousSwapEffect(final AudaciousSwapEffect effect) {
        super(effect);
    }

    @Override
    public AudaciousSwapEffect copy() {
        return new AudaciousSwapEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        Player player = game.getPlayer(permanent.getOwnerId());
        if (player == null) {
            return false;
        }
        player.putCardsOnTopOfLibrary(permanent, game, source, false);
        player.shuffleLibrary(source, game);
        Card card = player.getLibrary().getFromTop(game);
        if (card == null) {
            return true;
        }
        player.moveCards(card, Zone.EXILED, source, game);
        if (!card.isLand(game)) {
            CardUtil.castSpellWithAttributesForFree(player, source, game, card);
            return true;
        }
        if (player.chooseUse(Outcome.PutLandInPlay, "Put " + card.getName() + " onto the battlefield?", source, game)) {
            player.moveCards(card, Zone.BATTLEFIELD, source, game);
        }
        return true;
    }
}
