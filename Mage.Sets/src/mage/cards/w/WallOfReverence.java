

package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfYourEndStepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class WallOfReverence extends CardImpl {

    public WallOfReverence (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.WALL);

        this.power = new MageInt(1);
        this.toughness = new MageInt(6);
        this.addAbility(DefenderAbility.getInstance());
        this.addAbility(FlyingAbility.getInstance());
        Ability ability = new BeginningOfYourEndStepTriggeredAbility(new WallOfReverenceTriggeredEffect(), true);
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private WallOfReverence(final WallOfReverence card) {
        super(card);
    }

    @Override
    public WallOfReverence copy() {
        return new WallOfReverence(this);
    }
}

class WallOfReverenceTriggeredEffect extends OneShotEffect {
    WallOfReverenceTriggeredEffect() {
        super(Outcome.GainLife);
        staticText = "gain life equal to the power of target creature you control";
    }

    private WallOfReverenceTriggeredEffect(final WallOfReverenceTriggeredEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target = game.getPermanent(source.getFirstTarget());
        Player player = game.getPlayer(source.getControllerId());
        if (target != null && player != null) {
            player.gainLife(target.getPower().getValue(), game, source);
            return true;
        }
        return false;
    }

    @Override
    public WallOfReverenceTriggeredEffect copy() {
        return new WallOfReverenceTriggeredEffect(this);
    }

}