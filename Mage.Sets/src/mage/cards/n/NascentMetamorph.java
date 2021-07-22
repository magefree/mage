package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksOrBlocksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentCard;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.util.functions.EmptyCopyApplier;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NascentMetamorph extends CardImpl {

    public NascentMetamorph(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Nascent Metamorph attacks or blocks, target opponent reveals cards from the top of their library until they reveal a creature card. Nascent Metamorph becomes a copy of that card until end of turn. Then that player puts all cards revealed this way on the bottom of their library in a random order.
        Ability ability = new AttacksOrBlocksTriggeredAbility(
                new NascentMetamorphEffect(), false
        );
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private NascentMetamorph(final NascentMetamorph card) {
        super(card);
    }

    @Override
    public NascentMetamorph copy() {
        return new NascentMetamorph(this);
    }
}

class NascentMetamorphEffect extends OneShotEffect {

    NascentMetamorphEffect() {
        super(Outcome.Benefit);
        staticText = "target opponent reveals cards from the top of their library until they reveal a creature card. " +
                "{this} becomes a copy of that card until end of turn. " +
                "Then that player puts all cards revealed this way on the bottom of their library in a random order.";
    }

    private NascentMetamorphEffect(final NascentMetamorphEffect effect) {
        super(effect);
    }

    @Override
    public NascentMetamorphEffect copy() {
        return new NascentMetamorphEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player == null) {
            return false;
        }
        Cards toReveal = new CardsImpl();
        Card toCopy = null;
        for (Card card : player.getLibrary().getCards(game)) {
            toReveal.add(card);
            if (card == null || !card.isCreature(game)) {
                continue;
            }
            toCopy = card;
            break;
        }
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (toCopy != null && permanent != null) {
            game.copyPermanent(Duration.EndOfTurn, new PermanentCard(
                    toCopy, source.getControllerId(), game
            ), permanent.getId(), source, new EmptyCopyApplier());
        }
        player.revealCards(source, toReveal, game);
        player.putCardsOnBottomOfLibrary(toReveal, game, source, false);
        return true;
    }
}
