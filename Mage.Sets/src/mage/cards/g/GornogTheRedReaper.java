package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksPlayerWithCreaturesTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.combat.CowardsCantBlockWarriorsEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTypeTargetEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.ThatPlayerControlsTargetAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GornogTheRedReaper extends CardImpl {

    private static final FilterPermanent filterAttackWarrior = new FilterCreaturePermanent(SubType.WARRIOR, "Attacking Warriors");
    private static final FilterPermanent filterWarrior = new FilterControlledPermanent(SubType.WARRIOR, "Warriors you control");
    private static final FilterPermanent filterCoward = new FilterPermanent(SubType.COWARD, "Cowards your opponents control");

    static {
        filterAttackWarrior.add(AttackingPredicate.instance);
        filterCoward.add(TargetController.OPPONENT.getControllerPredicate());
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filterCoward, null);
    private static final Hint hint = new ValueHint(filterCoward.getMessage(), xValue);

    public GornogTheRedReaper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MINOTAUR);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Cowards can't block Warriors.
        this.addAbility(new SimpleStaticAbility(new CowardsCantBlockWarriorsEffect()));

        // Whenever one or more Warriors you control attack a player, target creature that player controls becomes a Coward.
        Ability ability = new AttacksPlayerWithCreaturesTriggeredAbility(
                new BecomesCreatureTypeTargetEffect(Duration.EndOfGame, SubType.COWARD).setText("target creature that player controls becomes a Coward"),
                filterWarrior, SetTargetPointer.PLAYER);
        ability.addTarget(new TargetPermanent());
        ability.setTargetAdjuster(new ThatPlayerControlsTargetAdjuster());
        this.addAbility(ability);

        // Attacking Warriors you control get +X/+0, where X is the number of Cowards your opponents control.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                xValue, StaticValue.get(0), Duration.WhileOnBattlefield, filterAttackWarrior, false
        )).addHint(hint));
    }

    private GornogTheRedReaper(final GornogTheRedReaper card) {
        super(card);
    }

    @Override
    public GornogTheRedReaper copy() {
        return new GornogTheRedReaper(this);
    }
}
