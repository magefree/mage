
package mage.cards.x;

import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author North
 */
public final class XathridDemon extends CardImpl {

    public XathridDemon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{B}{B}");
        this.subtype.add(SubType.DEMON);

        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(TrampleAbility.getInstance());
        // At the beginning of your upkeep, sacrifice a creature other than Xathrid Demon, then each opponent loses life equal to the sacrificed creature's power. If you can't sacrifice a creature, tap Xathrid Demon and you lose 7 life.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new XathridDemonEffect(), TargetController.YOU, false));
    }

    private XathridDemon(final XathridDemon card) {
        super(card);
    }

    @Override
    public XathridDemon copy() {
        return new XathridDemon(this);
    }
}

class XathridDemonEffect extends OneShotEffect {

    public XathridDemonEffect() {
        super(Outcome.Damage);
        this.staticText = "sacrifice a creature other than {this}, then each opponent loses life equal to the sacrificed creature's power. If you can't sacrifice a creature, tap {this} and you lose 7 life";
    }

    public XathridDemonEffect(final XathridDemonEffect effect) {
        super(effect);
    }

    @Override
    public XathridDemonEffect copy() {
        return new XathridDemonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (sourcePermanent == null) {
            sourcePermanent = (Permanent) game.getLastKnownInformation(source.getSourceId(), Zone.BATTLEFIELD);
        }
        if (controller == null || sourcePermanent == null) {
            return false;
        }

        FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("creature other than " + sourcePermanent.getName());
        filter.add(AnotherPredicate.instance);

        Target target = new TargetControlledCreaturePermanent(1, 1, filter, true);
        if (target.canChoose(controller.getId(), source, game)) {
            controller.choose(Outcome.Sacrifice, target, source, game);
            Permanent permanent = game.getPermanent(target.getFirstTarget());
            if (permanent != null) {
                int amount = permanent.getPower().getValue();
                permanent.sacrifice(source, game);

                if (amount > 0) {
                    Set<UUID> opponents = game.getOpponents(source.getControllerId());
                    for (UUID opponentId : opponents) {
                        Player opponent = game.getPlayer(opponentId);
                        if (opponent != null) {
                            opponent.loseLife(amount, game, source, false);
                        }
                    }
                }
                return true;
            }
        } else {
            sourcePermanent.tap(source, game);
            controller.loseLife(7, game, source, false);
            return true;
        }
        return false;
    }
}
