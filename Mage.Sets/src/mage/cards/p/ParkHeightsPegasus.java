package mage.cards.p;

import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.watchers.common.PermanentsEnteredBattlefieldWatcher;

/**
 *
 * @author weirddan455
 */
public final class ParkHeightsPegasus extends CardImpl {

    public ParkHeightsPegasus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{W}");

        this.subtype.add(SubType.PEGASUS);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever Park Heights Pegasus deals combat damage to a player, draw a card if two or more creatures entered the battlefield under your control this turn.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new ParkHeightsPegasusEffect(), false), new PermanentsEnteredBattlefieldWatcher());
    }

    private ParkHeightsPegasus(final ParkHeightsPegasus card) {
        super(card);
    }

    @Override
    public ParkHeightsPegasus copy() {
        return new ParkHeightsPegasus(this);
    }
}

class ParkHeightsPegasusEffect extends OneShotEffect {

    public ParkHeightsPegasusEffect() {
        super(Outcome.DrawCard);
        this.staticText = "draw a card if you had two or more creatures enter the battlefield under your control this turn";
    }

    private ParkHeightsPegasusEffect(final ParkHeightsPegasusEffect effect) {
        super(effect);
    }

    @Override
    public ParkHeightsPegasusEffect copy() {
        return new ParkHeightsPegasusEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        PermanentsEnteredBattlefieldWatcher watcher = game.getState().getWatcher(PermanentsEnteredBattlefieldWatcher.class);
        UUID controllerId = source.getControllerId();
        Player controller = game.getPlayer(controllerId);
        if (watcher != null && controller != null) {
            List<Permanent> permanents = watcher.getThisTurnEnteringPermanents(controllerId);
            if (permanents != null) {
                int creatures = 0;
                for (Permanent permanent : permanents) {
                    if (permanent.isCreature(game)) {
                        creatures++;
                        if (creatures >= 2) {
                            controller.drawCards(1, source, game);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
