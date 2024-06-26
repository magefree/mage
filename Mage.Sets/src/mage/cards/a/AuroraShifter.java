package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.util.functions.CopyApplier;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;

/**
 *
 * @author grimreap124
 */
public final class AuroraShifter extends CardImpl {

    public AuroraShifter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[] { CardType.CREATURE }, "{1}{U}");

        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever Aurora Shifter deals combat damage to a player, you get that many {E}.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new GetEnergyCountersControllerEffect(SavedDamageValue.MUCH)));

        // At the beginning of combat on your turn, you may pay {E}{E}. When you do, Aurora Shifter becomes a copy of another target creature you control, except it has this ability and "Whenever this creature deals combat damage to a player, you get that many {E}."
        ReflexiveTriggeredAbility reflexive = new ReflexiveTriggeredAbility(
                new AuroraShifterCopyEffect(),
                false);
        reflexive.addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE_YOU_CONTROL));

        this.addAbility(new BeginningOfCombatTriggeredAbility(
                new DoWhenCostPaid(reflexive, new PayEnergyCost(2),
                        "Pay {E}{E}? When you do, {this} becomes a copy of another target creature you control, except it has this ability and \"Whenever this creature deals combat damage to a player, you get that many {E}.\""),
                TargetController.YOU, false));
    }

    private AuroraShifter(final AuroraShifter card) {
        super(card);
    }

    @Override
    public AuroraShifter copy() {
        return new AuroraShifter(this);
    }
}

class AuroraShifterCopyEffect extends OneShotEffect {

    AuroraShifterCopyEffect() {
        super(Outcome.Copy);
        this.staticText = "{this} becomes a copy of another target creature you control, except it has this ability and \"Whenever this creature deals combat damage to a player, you get that many {E}.\"";
    }

    private AuroraShifterCopyEffect(final AuroraShifterCopyEffect effect) {
        super(effect);
    }

    @Override
    public AuroraShifterCopyEffect copy() {
        return new AuroraShifterCopyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        Permanent copyFromPermanent = game.getPermanent(getTargetPointer().getFirst(game, source));

        if (sourcePermanent != null && copyFromPermanent != null) {
            game.copyPermanent(Duration.WhileOnBattlefield, copyFromPermanent, sourcePermanent.getId(), source,
                    new AuroraShifterCopyApplier());
            return true;
        }
        return false;
    }
}

class AuroraShifterCopyApplier extends CopyApplier {

    @Override
    public boolean apply(Game game, MageObject blueprint, Ability source, UUID copyToObjectId) {

        // At the beginning of combat on your turn, you may pay {E}{E}. When you do, Aurora Shifter becomes a copy of another target creature you control, except it has this ability and "Whenever this creature deals combat damage to a player, you get that many {E}."
        ReflexiveTriggeredAbility reflexive = new ReflexiveTriggeredAbility(
                new AuroraShifterCopyEffect(),
                false);
        reflexive.addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE_YOU_CONTROL));

        blueprint.getAbilities().add(new BeginningOfCombatTriggeredAbility(
                new DoWhenCostPaid(reflexive, new PayEnergyCost(2),
                        "Pay {E}{E}? When you do, {this} becomes a copy of another target creature you control, except it has this ability and \"Whenever this creature deals combat damage to a player, you get that many {E}.\""),
                TargetController.YOU, false));


        blueprint.getAbilities().add(new DealsCombatDamageToAPlayerTriggeredAbility(
                new GetEnergyCountersControllerEffect(SavedDamageValue.MANY)));
        return true;
    }
}