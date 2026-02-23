package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.AttacksAttachedTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SiegeAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InvasionOfNewCapenna extends TransformingDoubleFacedCard {

    public InvasionOfNewCapenna(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.BATTLE}, new SubType[]{SubType.SIEGE}, "{W}{B}",
                "Holy Frazzle-Cannon",
                new CardType[]{CardType.ARTIFACT}, new SubType[]{SubType.EQUIPMENT}, "WB"
        );

        // Invasion of New Capenna
        this.getLeftHalfCard().setStartingDefense(4);

        // (As a Siege enters, choose an opponent to protect it. You and others can attack it. When it's defeated, exile it, then cast it transformed.)
        this.getLeftHalfCard().addAbility(new SiegeAbility());

        // When Invasion of New Capenna enters the battlefield, you may sacrifice an artifact or creature. When you do, exile target artifact or creature an opponent controls.
        ReflexiveTriggeredAbility ref = new ReflexiveTriggeredAbility(new ExileTargetEffect(), false);
        ref.addTarget(new TargetPermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_ARTIFACT_OR_CREATURE));
        this.getLeftHalfCard().addAbility(new EntersBattlefieldTriggeredAbility(new DoWhenCostPaid(
                ref,
                new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_CREATURE),
                "Sacrifice an artifact or creature?"
        )));

        // Holy Frazzle-Cannon
        // Whenever equipped creature attacks, put a +1/+1 counter on that creature and each other creature you control that shares a creature type with it.
        this.getRightHalfCard().addAbility(new AttacksAttachedTriggeredAbility(
                new HolyFrazzleCannonEffect(), AttachmentType.EQUIPMENT,
                false, SetTargetPointer.PERMANENT
        ));

        // Equip {1}
        this.getRightHalfCard().addAbility(new EquipAbility(1, false));
    }

    private InvasionOfNewCapenna(final InvasionOfNewCapenna card) {
        super(card);
    }

    @Override
    public InvasionOfNewCapenna copy() {
        return new InvasionOfNewCapenna(this);
    }
}

class HolyFrazzleCannonEffect extends OneShotEffect {

    HolyFrazzleCannonEffect() {
        super(Outcome.Benefit);
        staticText = "put a +1/+1 counter on that creature and " +
                "each other creature you control that shares a creature type with it";
    }

    private HolyFrazzleCannonEffect(final HolyFrazzleCannonEffect effect) {
        super(effect);
    }

    @Override
    public HolyFrazzleCannonEffect copy() {
        return new HolyFrazzleCannonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        for (Permanent creature : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_CONTROLLED_CREATURE, source.getControllerId(), source, game
        )) {
            if (creature.equals(permanent) || permanent.shareCreatureTypes(game, creature)) {
                creature.addCounters(CounterType.P1P1.createInstance(), source, game);
            }
        }
        return true;
    }
}
