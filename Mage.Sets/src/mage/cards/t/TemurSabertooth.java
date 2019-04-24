
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public final class TemurSabertooth extends CardImpl {

    public TemurSabertooth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{G}");
        this.subtype.add(SubType.CAT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // {1}{G}: You may return another creature you control to its owner's hand. If you do, Temur Sabertooth gains indestructible until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new TemurSabertoothEffect(), new ManaCostsImpl("{1}{G}")));

    }

    public TemurSabertooth(final TemurSabertooth card) {
        super(card);
    }

    @Override
    public TemurSabertooth copy() {
        return new TemurSabertooth(this);
    }
}

class TemurSabertoothEffect extends OneShotEffect {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("another creature you control");

    static {
        filter.add(new AnotherPredicate());
    }

    public TemurSabertoothEffect() {
        super(Outcome.Detriment);
        this.staticText = "You may return another creature you control to its owner's hand. If you do, {this} gains indestructible until end of turn";
    }

    public TemurSabertoothEffect(final TemurSabertoothEffect effect) {
        super(effect);
    }

    @Override
    public TemurSabertoothEffect copy() {
        return new TemurSabertoothEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Target target = new TargetPermanent(1, 1, filter, true);
            if (target.canChoose(source.getSourceId(), controller.getId(), game)) {
                if (controller.chooseUse(outcome, "Return another creature to hand?", source, game)
                        && controller.chooseTarget(outcome, target, source, game)) {
                    Permanent toHand = game.getPermanent(target.getFirstTarget());
                    if (toHand != null) {
                        controller.moveCards(toHand, Zone.HAND, source, game);
                    }
                    game.addEffect(new GainAbilitySourceEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn), source);
                }
            }
            return true;
        }
        return false;
    }
}
