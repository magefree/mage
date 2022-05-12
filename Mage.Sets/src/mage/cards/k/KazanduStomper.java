package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KazanduStomper extends CardImpl {

    public KazanduStomper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}");

        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Kazandu Stomper enters the battlefield, return up to two lands you control to their owner's hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new KazanduStomperEffect()));
    }

    private KazanduStomper(final KazanduStomper card) {
        super(card);
    }

    @Override
    public KazanduStomper copy() {
        return new KazanduStomper(this);
    }
}

class KazanduStomperEffect extends OneShotEffect {

    KazanduStomperEffect() {
        super(Outcome.Benefit);
        staticText = "return up to two lands you control to their owner's hand";
    }

    private KazanduStomperEffect(final KazanduStomperEffect effect) {
        super(effect);
    }

    @Override
    public KazanduStomperEffect copy() {
        return new KazanduStomperEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetPermanent target = new TargetPermanent(
                0, 2, StaticFilters.FILTER_CONTROLLED_PERMANENT_LANDS, true
        );
        player.choose(outcome, target, source, game);
        return player.moveCards(new CardsImpl(target.getTargets()), Zone.HAND, source, game);
    }
}
