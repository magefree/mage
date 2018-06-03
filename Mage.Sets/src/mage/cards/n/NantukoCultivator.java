
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.common.FilterLandCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author LoneFox

 */
public final class NantukoCultivator extends CardImpl {

    public NantukoCultivator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.INSECT);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Nantuko Cultivator enters the battlefield, you may discard any number of land cards. Put that many +1/+1 counters on Nantuko Cultivator and draw that many cards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new NantukoCultivatorEffect(), true));
    }

    public NantukoCultivator(final NantukoCultivator card) {
        super(card);
    }

    @Override
    public NantukoCultivator copy() {
        return new NantukoCultivator(this);
    }
}

class NantukoCultivatorEffect extends OneShotEffect {

    public NantukoCultivatorEffect() {
        super(Outcome.BoostCreature);
        staticText  = "you may discard any number of land cards. Put that many +1/+1 counters on {this} and draw that many cards.";
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if(player != null) {
            TargetCardInHand toDiscard = new TargetCardInHand(0, Integer.MAX_VALUE, new FilterLandCard());
            if(player.chooseTarget(Outcome.Discard, toDiscard, source, game)) {
                int count = 0;
                for(UUID targetId: toDiscard.getTargets()) {
                    player.discard(game.getCard(targetId), source, game);
                    count++;
                }
                Permanent permanent = game.getPermanent(source.getSourceId());
                if(permanent != null) {
                    permanent.addCounters(CounterType.P1P1.createInstance(count), source, game);
                }
                player.drawCards(count, game);
            }
            return true;
        }
        return false;
    }

    public NantukoCultivatorEffect(final NantukoCultivatorEffect effect) {
        super(effect);
    }

    @Override
    public NantukoCultivatorEffect copy() {
        return new NantukoCultivatorEffect(this);
    }

}
