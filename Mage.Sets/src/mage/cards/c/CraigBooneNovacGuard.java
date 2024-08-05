package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.PutCountersSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class CraigBooneNovacGuard extends CardImpl {

    public CraigBooneNovacGuard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // One for My Baby -- Whenever you attack with two or more creatures, put two quest counters on Craig Boone, Novac Guard. When you do, Craig Boone deals damage equal to the number of quest counters on it to up to one target creature unless that creature's controller has Craig Boone deal that much damage to them.
        ReflexiveTriggeredAbility reflexive = new ReflexiveTriggeredAbility(new CraigBooneNovacGuardEffect(), false);
        reflexive.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(
                new DoWhenCostPaid(
                        reflexive,
                        new PutCountersSourceCost(CounterType.QUEST.createInstance(2)),
                        "", false
                ), 2
        ).withFlavorWord("One for My Baby"));
    }

    private CraigBooneNovacGuard(final CraigBooneNovacGuard card) {
        super(card);
    }

    @Override
    public CraigBooneNovacGuard copy() {
        return new CraigBooneNovacGuard(this);
    }
}

class CraigBooneNovacGuardEffect extends OneShotEffect {

    CraigBooneNovacGuardEffect() {
        super(Outcome.Damage);
        staticText = "{this} deals damage equal to the number of quest counters on it "
                + "to up to one target creature unless that creature's controller "
                + "has {this} deal that much damage to them";
    }

    private CraigBooneNovacGuardEffect(final CraigBooneNovacGuardEffect effect) {
        super(effect);
    }

    @Override
    public CraigBooneNovacGuardEffect copy() {
        return new CraigBooneNovacGuardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetPermanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        Permanent sourcePermanent = source.getSourcePermanentOrLKI(game);
        if (sourcePermanent == null || targetPermanent == null) {
            return false;
        }
        int amount = sourcePermanent.getCounters(game).getCount(CounterType.QUEST);
        Player playerChoosing = game.getPlayer(targetPermanent.getControllerId());
        if (playerChoosing != null && playerChoosing.chooseUse(
                Outcome.Neutral,
                "have " + amount + " damage be dealt to you instead of " + targetPermanent.getLogName() + "?",
                source,
                game)
        ) {
            new DamageTargetEffect(amount)
                    .setTargetPointer(new FixedTarget(playerChoosing.getId()))
                    .apply(game, source);
            return true;
        }
        new DamageTargetEffect(amount)
                .setTargetPointer(getTargetPointer().copy())
                .apply(game, source);
        return true;
    }

}
