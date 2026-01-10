package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.ColorsAmongControlledPermanentsCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.PlayAdditionalLandsControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PrismaticUndercurrents extends CardImpl {

    public PrismaticUndercurrents(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}");

        // Vivid -- When this enchantment enters, search your library for up to X basic land cards, where X is the number of colors among permanents you control. Reveal those cards, put them into your hand, then shuffle.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new PrismaticUndercurrentsEffect())
                .setAbilityWord(AbilityWord.VIVID)
                .addHint(ColorsAmongControlledPermanentsCount.ALL_PERMANENTS.getHint()));

        // You may play an additional land on each of your turns.
        this.addAbility(new SimpleStaticAbility(
                new PlayAdditionalLandsControllerEffect(1, Duration.WhileOnBattlefield)
        ));
    }

    private PrismaticUndercurrents(final PrismaticUndercurrents card) {
        super(card);
    }

    @Override
    public PrismaticUndercurrents copy() {
        return new PrismaticUndercurrents(this);
    }
}

class PrismaticUndercurrentsEffect extends OneShotEffect {

    PrismaticUndercurrentsEffect() {
        super(Outcome.Benefit);
        staticText = "search your library for up to X basic land cards, where X is the number of colors " +
                "among permanents you control. Reveal those cards, put them into your hand, then shuffle";
    }

    private PrismaticUndercurrentsEffect(final PrismaticUndercurrentsEffect effect) {
        super(effect);
    }

    @Override
    public PrismaticUndercurrentsEffect copy() {
        return new PrismaticUndercurrentsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCardInLibrary target = new TargetCardInLibrary(
                0,
                ColorsAmongControlledPermanentsCount
                        .ALL_PERMANENTS
                        .calculate(game, source, this),
                StaticFilters.FILTER_CARD_BASIC_LANDS
        );
        player.searchLibrary(target, source, game);
        Cards cards = new CardsImpl(target.getTargets());
        cards.retainZone(Zone.LIBRARY, game);
        player.revealCards(source, cards, game);
        player.moveCards(cards, Zone.HAND, source, game);
        player.shuffleLibrary(source, game);
        return true;
    }
}
