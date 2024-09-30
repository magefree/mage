package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesBlockedAllTriggeredAbility;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NorinSwiftSurvivalist extends CardImpl {

    public NorinSwiftSurvivalist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.COWARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Norin, Swift Survivalist can't block.
        this.addAbility(new CantBlockAbility());

        // Whenever a creature you control becomes blocked, you may exile it. You may play that card from exile this turn.
        this.addAbility(new BecomesBlockedAllTriggeredAbility(
                new NorinSwiftSurvivalistEffect(), true,
                StaticFilters.FILTER_CONTROLLED_A_CREATURE, true
        ));
    }

    private NorinSwiftSurvivalist(final NorinSwiftSurvivalist card) {
        super(card);
    }

    @Override
    public NorinSwiftSurvivalist copy() {
        return new NorinSwiftSurvivalist(this);
    }
}

class NorinSwiftSurvivalistEffect extends OneShotEffect {

    NorinSwiftSurvivalistEffect() {
        super(Outcome.Benefit);
        staticText = "exile it. You may play that card from exile this turn";
    }

    private NorinSwiftSurvivalistEffect(final NorinSwiftSurvivalistEffect effect) {
        super(effect);
    }

    @Override
    public NorinSwiftSurvivalistEffect copy() {
        return new NorinSwiftSurvivalistEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (player == null || permanent == null) {
            return false;
        }
        Card card = permanent.getMainCard();
        player.moveCards(permanent, Zone.EXILED, source, game);
        if (card != null) {
            CardUtil.makeCardPlayable(
                    game, source, card, false,
                    Duration.EndOfTurn, false, player.getId(), null
            );
        }
        return true;
    }
}
