package mage.cards.z;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DontUntapInControllersUntapStepSourceEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.constants.*;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author NinthWorld
 */
public final class ZergLurker extends CardImpl {

    public ZergLurker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        
        this.subtype.add(SubType.ZERG);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Zerg Lurker doesn't untap during your untap step.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DontUntapInControllersUntapStepSourceEffect()));

        // At the beginning of your upkeep, if Zerg Lurker is tapped, target opponent loses 2 life.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new ZergLurkerEffect(), TargetController.YOU, false));
    }

    public ZergLurker(final ZergLurker card) {
        super(card);
    }

    @Override
    public ZergLurker copy() {
        return new ZergLurker(this);
    }
}

class ZergLurkerEffect extends OneShotEffect {

    public ZergLurkerEffect() {
        super(Outcome.LoseLife);
    }

    public ZergLurkerEffect(final ZergLurkerEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (you != null && permanent != null && permanent.isTapped()) {
            TargetOpponent target = new TargetOpponent();
            if(you.chooseTarget(Outcome.LoseLife, target, source, game)) {
                Player opponent = game.getPlayer(target.getFirstTarget());
                if(opponent != null) {
                    opponent.loseLife(2, game, false);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public ZergLurkerEffect copy() {
        return new ZergLurkerEffect(this);
    }
}
