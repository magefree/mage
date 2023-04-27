package mage.cards.j;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.CreaturesAttackingYouCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PreventAllDamageByAllPermanentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JaheirasRespite extends CardImpl {

    public JaheirasRespite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{G}");

        // Search your library for up to X basic land cards, where X is the number of creatures attacking you, put those cards onto the battlefield tapped, then shuffle.
        this.getSpellAbility().addEffect(new JaheirasRespiteEffect());
        this.getSpellAbility().addHint(CreaturesAttackingYouCount.getHint());

        // Prevent all combat damage that would be dealt this turn.
        this.getSpellAbility().addEffect(new PreventAllDamageByAllPermanentsEffect(Duration.EndOfTurn, true).concatBy("<br>"));
    }

    private JaheirasRespite(final JaheirasRespite card) {
        super(card);
    }

    @Override
    public JaheirasRespite copy() {
        return new JaheirasRespite(this);
    }
}

class JaheirasRespiteEffect extends OneShotEffect {

    JaheirasRespiteEffect() {
        super(Outcome.Benefit);
        staticText = "search your library for up to X basic land cards, where X is the number " +
                "of creatures attacking you, put those cards onto the battlefield tapped, then shuffle";
    }

    private JaheirasRespiteEffect(final JaheirasRespiteEffect effect) {
        super(effect);
    }

    @Override
    public JaheirasRespiteEffect copy() {
        return new JaheirasRespiteEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int count = CreaturesAttackingYouCount.instance.calculate(game, source, this);
        if (count == 0) {
            return false;
        }
        TargetCardInLibrary target = new TargetCardInLibrary(0, count, StaticFilters.FILTER_CARD_BASIC_LANDS);
        player.searchLibrary(target, source, game);
        Cards cards = new CardsImpl();
        target.getTargets()
                .stream()
                .map(cardId -> player.getLibrary().getCard(cardId, game))
                .forEach(cards::add);
        player.moveCards(
                cards.getCards(game), Zone.BATTLEFIELD, source, game,
                true, false, false, null
        );
        player.shuffleLibrary(source, game);
        return true;
    }
}
