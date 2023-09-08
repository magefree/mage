
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfYourEndStepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author North
 */
public final class SphinxSovereign extends CardImpl {

    public SphinxSovereign(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{4}{W}{U}{U}{B}");
        this.subtype.add(SubType.SPHINX);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // At the beginning of your end step, you gain 3 life if Sphinx Sovereign is untapped. Otherwise, each opponent loses 3 life.
        this.addAbility(new BeginningOfYourEndStepTriggeredAbility(new SphinxSovereignEffect(), false));
    }

    private SphinxSovereign(final SphinxSovereign card) {
        super(card);
    }

    @Override
    public SphinxSovereign copy() {
        return new SphinxSovereign(this);
    }
}

class SphinxSovereignEffect extends OneShotEffect {

    public SphinxSovereignEffect() {
        super(Outcome.Benefit);
        this.staticText = "you gain 3 life if {this} is untapped. Otherwise, each opponent loses 3 life";
    }

    private SphinxSovereignEffect(final SphinxSovereignEffect effect) {
        super(effect);
    }

    @Override
    public SphinxSovereignEffect copy() {
        return new SphinxSovereignEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = (Permanent) source.getSourceObject(game);
        if (controller != null && permanent != null) {
            if (!permanent.isTapped()) {
                controller.gainLife(3, game, source);
            } else {
                for (UUID opponentId : game.getState().getPlayersInRange(controller.getId(), game)) {
                    if (controller.hasOpponent(opponentId, game)) {
                    Player opponent = game.getPlayer(opponentId);
                        if (opponent != null) {
                            opponent.loseLife(3, game, source, false);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
