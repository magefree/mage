package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.ColorsAmongControlledPermanentsCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.*;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AuroraAwakener extends CardImpl {

    public AuroraAwakener(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{G}");

        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Vivid -- When this creature enters, reveal cards from the top of your library until you reveal X permanent cards, where X is the number of colors among permanents you control. Put any number of those permanent cards onto the battlefield, then put the rest of the revealed cards on the bottom of your library in a random order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new AuroraAwakenerEffect())
                .setAbilityWord(AbilityWord.VIVID)
                .addHint(ColorsAmongControlledPermanentsCount.ALL_PERMANENTS.getHint()));
    }

    private AuroraAwakener(final AuroraAwakener card) {
        super(card);
    }

    @Override
    public AuroraAwakener copy() {
        return new AuroraAwakener(this);
    }
}

class AuroraAwakenerEffect extends OneShotEffect {

    AuroraAwakenerEffect() {
        super(Outcome.Benefit);
        staticText = "reveal cards from the top of your library until you reveal X permanent cards, " +
                "where X is the number of colors among permanents you control. " +
                "Put any number of those permanent cards onto the battlefield, " +
                "then put the rest of the revealed cards on the bottom of your library in a random order";
    }

    private AuroraAwakenerEffect(final AuroraAwakenerEffect effect) {
        super(effect);
    }

    @Override
    public AuroraAwakenerEffect copy() {
        return new AuroraAwakenerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        int count = ColorsAmongControlledPermanentsCount.ALL_PERMANENTS.calculate(game, source, this);
        if (player == null || count < 1) {
            return false;
        }
        Cards cards = new CardsImpl();
        for (Card card : player.getLibrary().getCards(game)) {
            cards.add(card);
            if (cards.count(StaticFilters.FILTER_CARD_PERMANENT, game) >= count) {
                break;
            }
        }
        player.revealCards(source, cards, game);
        TargetCard target = new TargetCardInLibrary(0, Integer.MAX_VALUE, StaticFilters.FILTER_CARD_PERMANENTS);
        player.choose(outcome, cards, target, source, game);
        player.moveCards(new CardsImpl(target.getTargets()), Zone.BATTLEFIELD, source, game);
        cards.retainZone(Zone.LIBRARY, game);
        player.putCardsOnBottomOfLibrary(cards, game, source, false);
        return true;
    }
}
