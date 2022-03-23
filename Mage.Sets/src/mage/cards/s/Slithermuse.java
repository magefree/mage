
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.EvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public final class Slithermuse extends CardImpl {

    public Slithermuse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}{U}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Slithermuse leaves the battlefield, choose an opponent. If that player has more cards in hand than you, draw cards equal to the difference.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new SlithermuseEffect(), false));
        // Evoke {3}{U}
        this.addAbility(new EvokeAbility("{3}{U}"));
    }

    private Slithermuse(final Slithermuse card) {
        super(card);
    }

    @Override
    public Slithermuse copy() {
        return new Slithermuse(this);
    }
}

/**
 * FAQ
 * You choose an opponent when the ability resolves. Once you determine how many
 * more cards than you that player has, that number is locked in as the amount
 * you'll draw.
 *
 */
class SlithermuseEffect extends OneShotEffect {

    public SlithermuseEffect() {
        super(Outcome.Benefit);
        this.staticText = "choose an opponent. If that player has more cards in hand than you, draw cards equal to the difference";
    }

    public SlithermuseEffect(final SlithermuseEffect effect) {
        super(effect);
    }

    @Override
    public SlithermuseEffect copy() {
        return new SlithermuseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = (Permanent)game.getLastKnownInformation(source.getSourceId(), Zone.BATTLEFIELD);
        if (player != null && permanent != null) {
            TargetOpponent target = new TargetOpponent();
            target.setNotTarget(true);
            if (player.choose(this.outcome, target, source, game)) {
                Player chosenPlayer = game.getPlayer(target.getFirstTarget());
                if (chosenPlayer != null) {
                    game.informPlayers(permanent.getName() + ": " + player.getLogName() + " has chosen " + chosenPlayer.getLogName());
                    int diff = chosenPlayer.getHand().size() - player.getHand().size();
                    if (diff > 0) {
                        player.drawCards(diff, source, game);
                    }
                    return true;
                }
            }
        }
        return false;
    }
}
