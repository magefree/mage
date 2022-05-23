package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThrakkusTheButcher extends CardImpl {

    public ThrakkusTheButcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);
        this.subtype.add(SubType.PEASANT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever Thrakkus the Butcher attacks, double the power of each dragon you control until end of turn.
        this.addAbility(new AttacksTriggeredAbility(new ThrakkusTheButcherEffect()));
    }

    private ThrakkusTheButcher(final ThrakkusTheButcher card) {
        super(card);
    }

    @Override
    public ThrakkusTheButcher copy() {
        return new ThrakkusTheButcher(this);
    }
}

class ThrakkusTheButcherEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent(SubType.DRAGON);

    ThrakkusTheButcherEffect() {
        super(Outcome.Benefit);
        staticText = "double the power of each dragon you control until end of turn";
    }

    private ThrakkusTheButcherEffect(final ThrakkusTheButcherEffect effect) {
        super(effect);
    }

    @Override
    public ThrakkusTheButcherEffect copy() {
        return new ThrakkusTheButcherEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                filter, source.getControllerId(), source, game
        )) {
            if (permanent.getPower().getValue() != 0) {
                game.addEffect(new BoostTargetEffect(
                        permanent.getPower().getValue(), 0
                ).setTargetPointer(new FixedTarget(permanent, game)), source);
            }
        }
        return true;
    }
}
