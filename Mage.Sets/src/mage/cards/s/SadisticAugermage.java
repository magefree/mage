
package mage.cards.s;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 * @author LevelX2
 */
public final class SadisticAugermage extends CardImpl {

    public SadisticAugermage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // When Sadistic Augermage dies, each player puts a card from their hand on top of their library.
        this.addAbility(new DiesSourceTriggeredAbility(new WidespreadPanicEffect()));
    }

    private SadisticAugermage(final SadisticAugermage card) {
        super(card);
    }

    @Override
    public SadisticAugermage copy() {
        return new SadisticAugermage(this);
    }
}

class WidespreadPanicEffect extends OneShotEffect {

    public WidespreadPanicEffect() {
        super(Outcome.Detriment);
        this.staticText = "each player puts a card from their hand on top of their library";
    }

    public WidespreadPanicEffect(final WidespreadPanicEffect effect) {
        super(effect);
    }

    @Override
    public WidespreadPanicEffect copy() {
        return new WidespreadPanicEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    if (!player.getHand().isEmpty()) {
                        TargetCardInHand target = new TargetCardInHand();
                        target.setTargetName("a card from your hand to put on top of your library");
                        player.choose(Outcome.Detriment, target, source, game);
                        Card card = player.getHand().get(target.getFirstTarget(), game);
                        if (card != null) {
                            player.moveCardToLibraryWithInfo(card, source, game, Zone.HAND, true, false);
                        }
                    }
                }
            }
            return true;
        }

        return false;
    }
}
