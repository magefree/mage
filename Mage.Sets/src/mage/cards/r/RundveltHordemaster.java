package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesThisOrAnotherCreatureTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RundveltHordemaster extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.GOBLIN, "Goblins");
    private static final FilterPermanent filter2 = new FilterControlledPermanent(SubType.GOBLIN);

    public RundveltHordemaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Other Goblins you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                1, 1, Duration.WhileOnBattlefield, filter, true
        )));

        // Whenever Rundvelt Hordemaster or another Goblin you control dies, exile the top card of your library. If it's a Goblin creature card, you may cast that card until the end of your next turn.
        this.addAbility(new DiesThisOrAnotherCreatureTriggeredAbility(
                new RundveltHordemasterEffect(), false, filter2
        ));
    }

    private RundveltHordemaster(final RundveltHordemaster card) {
        super(card);
    }

    @Override
    public RundveltHordemaster copy() {
        return new RundveltHordemaster(this);
    }
}

class RundveltHordemasterEffect extends OneShotEffect {

    RundveltHordemasterEffect() {
        super(Outcome.Benefit);
        staticText = "exile the top card of your library. If it's a Goblin creature card, " +
                "you may cast that card until the end of your next turn";
    }

    private RundveltHordemasterEffect(final RundveltHordemasterEffect effect) {
        super(effect);
    }

    @Override
    public RundveltHordemasterEffect copy() {
        return new RundveltHordemasterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Card card = player.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }
        player.moveCards(card, Zone.EXILED, source, game);
        if (card.isCreature(game) && card.hasSubtype(SubType.GOBLIN, game)) {
            CardUtil.makeCardPlayable(game, source, card, Duration.UntilEndOfYourNextTurn, false);
        }
        return true;
    }
}
