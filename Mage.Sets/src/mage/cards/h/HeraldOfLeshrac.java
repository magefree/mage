package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.card.OwnerIdPredicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class HeraldOfLeshrac extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledLandPermanent("land you control but don't own");

    static {
        filter.add(TargetController.NOT_YOU.getOwnerPredicate());
    }

    public HeraldOfLeshrac(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{B}");

        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Cumulative upkeep-Gain control of a land you don't control.
        this.addAbility(new CumulativeUpkeepAbility(new HeraldOfLeshracCumulativeCost()));

        // Herald of Leshrac gets +1/+1 for each land you control but don't own.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostSourceEffect(new PermanentsOnBattlefieldCount(filter), new PermanentsOnBattlefieldCount(filter), Duration.WhileOnBattlefield)));

        // When Herald of Leshrac leaves the battlefield, each player gains control of each land they own that you control.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new HeraldOfLeshracLeavesEffect(), false));
    }

    private HeraldOfLeshrac(final HeraldOfLeshrac card) {
        super(card);
    }

    @Override
    public HeraldOfLeshrac copy() {
        return new HeraldOfLeshrac(this);
    }
}

class HeraldOfLeshracCumulativeCost extends CostImpl {

    private static final FilterPermanent filter = new FilterLandPermanent("land you don't control");

    static {
        filter.add(TargetController.NOT_YOU.getControllerPredicate());
    }

    HeraldOfLeshracCumulativeCost() {
        this.text = "Gain control of a land you don't control";
    }

    HeraldOfLeshracCumulativeCost(final HeraldOfLeshracCumulativeCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Target target = new TargetPermanent(filter);
        if (target.choose(Outcome.GainControl, controllerId, source.getSourceId(), game)) {
            ContinuousEffect effect = new GainControlTargetEffect(Duration.EndOfGame);
            effect.setTargetPointer(new FixedTarget(target.getFirstTarget(), game));
            game.addEffect(effect, ability);
            game.getState().processAction(game);
            paid = true;
        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return game.getBattlefield().contains(filter, source.getSourceId(), controllerId, game, 1);
    }

    @Override
    public HeraldOfLeshracCumulativeCost copy() {
        return new HeraldOfLeshracCumulativeCost(this);
    }
}

class HeraldOfLeshracLeavesEffect extends OneShotEffect {

    HeraldOfLeshracLeavesEffect() {
        super(Outcome.Detriment);
        this.staticText = "each player gains control of each land they own that you control";
    }

    HeraldOfLeshracLeavesEffect(final HeraldOfLeshracLeavesEffect effect) {
        super(effect);
    }

    @Override
    public HeraldOfLeshracLeavesEffect copy() {
        return new HeraldOfLeshracLeavesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            if (playerId.equals(source.getControllerId())) {
                continue;
            }
            FilterPermanent filter = new FilterLandPermanent();
            filter.add(new OwnerIdPredicate(playerId));
            filter.add(new ControllerIdPredicate(source.getControllerId()));
            for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
                ContinuousEffect effect = new GainControlTargetEffect(Duration.EndOfGame, playerId);
                effect.setTargetPointer(new FixedTarget(permanent, game));
                game.addEffect(effect, source);
            }
        }
        return true;
    }
}
