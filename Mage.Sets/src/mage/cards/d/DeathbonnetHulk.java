package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DeathbonnetHulk extends CardImpl {

    public DeathbonnetHulk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.FUNGUS);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.color.setGreen(true);
        this.transformable = true;
        this.nightCard = true;

        // At the beginning of your upkeep, you may exile a card from a graveyard. If a creature card was exiled this way, put a +1/+1 counter on Deathbonnet Hulk.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new DeathbonnetHulkEffect(), TargetController.YOU, false
        ));
    }

    private DeathbonnetHulk(final DeathbonnetHulk card) {
        super(card);
    }

    @Override
    public DeathbonnetHulk copy() {
        return new DeathbonnetHulk(this);
    }
}

class DeathbonnetHulkEffect extends OneShotEffect {

    DeathbonnetHulkEffect() {
        super(Outcome.Benefit);
        staticText = "you may exile a card from a graveyard. " +
                "If a creature card was exiled this way, put a +1/+1 counter on {this}";
    }

    private DeathbonnetHulkEffect(final DeathbonnetHulkEffect effect) {
        super(effect);
    }

    @Override
    public DeathbonnetHulkEffect copy() {
        return new DeathbonnetHulkEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCard target = new TargetCardInGraveyard(0, 1);
        target.setNotTarget(true);
        player.choose(outcome, target, source.getSourceId(), game);
        Card card = game.getCard(target.getFirstTarget());
        if (card == null) {
            return false;
        }
        boolean flag = card.isCreature(game);
        player.moveCards(card, Zone.EXILED, source, game);
        if (!flag) {
            return true;
        }
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent != null) {
            permanent.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game);
        }
        return true;
    }
}
