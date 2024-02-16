package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WreckAndRebuild extends CardImpl {

    public WreckAndRebuild(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}{G}");

        // Choose one --
        // * Destroy target artifact or enchantment.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));

        // * Mill five cards, then you may put a land card from your graveyard onto the battlefield tapped.
        this.getSpellAbility().addMode(new Mode(new MillCardsControllerEffect(5)).addEffect(new WreckAndRebuildEffect()));

        // Flashback {3}{R}{G}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{3}{R}{G}")));
    }

    private WreckAndRebuild(final WreckAndRebuild card) {
        super(card);
    }

    @Override
    public WreckAndRebuild copy() {
        return new WreckAndRebuild(this);
    }
}

class WreckAndRebuildEffect extends OneShotEffect {

    WreckAndRebuildEffect() {
        super(Outcome.Benefit);
        staticText = ", then you may put a land card from your graveyard onto the battlefield tapped";
    }

    private WreckAndRebuildEffect(final WreckAndRebuildEffect effect) {
        super(effect);
    }

    @Override
    public WreckAndRebuildEffect copy() {
        return new WreckAndRebuildEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCard targetCard = new TargetCardInYourGraveyard(
                0, 1, StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD, true
        );
        player.choose(outcome, targetCard, source, game);
        Card card = game.getCard(targetCard.getFirstTarget());
        return card != null && player.moveCards(
                card, Zone.BATTLEFIELD, source, game, true,
                false, false, null
        );
    }
}
