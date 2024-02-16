package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TazeemRaptor extends CardImpl {

    public TazeemRaptor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Tazeem Raptor enters the battlefield, you may return a land you control to its owner's hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new TazeemRaptorEffect(), true));
    }

    private TazeemRaptor(final TazeemRaptor card) {
        super(card);
    }

    @Override
    public TazeemRaptor copy() {
        return new TazeemRaptor(this);
    }
}

class TazeemRaptorEffect extends OneShotEffect {

    TazeemRaptorEffect() {
        super(Outcome.Benefit);
        staticText = "return a land you control to its owner's hand";
    }

    private TazeemRaptorEffect(final TazeemRaptorEffect effect) {
        super(effect);
    }

    @Override
    public TazeemRaptorEffect copy() {
        return new TazeemRaptorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetPermanent target = new TargetControlledPermanent(
                0, 1, StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND, true
        );
        player.choose(outcome, target, source, game);
        return player.moveCards(game.getCard(target.getFirstTarget()), Zone.HAND, source, game);
    }
}
