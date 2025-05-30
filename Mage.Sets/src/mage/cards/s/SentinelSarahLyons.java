package mage.cards.s;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.ArtifactEnteredUnderYourControlCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.dynamicvalue.common.ArtifactYouControlCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.common.ArtifactYouControlHint;
import mage.abilities.keyword.BattalionAbility;
import mage.constants.*;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.target.TargetPlayer;
import mage.watchers.common.ArtifactEnteredControllerWatcher;

/**
 * @author Cguy7777
 */
public final class SentinelSarahLyons extends CardImpl {

    public SentinelSarahLyons(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // As long as an artifact entered the battlefield under your control this turn, creatures you control get +2/+2.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                        new BoostControlledEffect(2, 2, Duration.WhileOnBattlefield),
                        ArtifactEnteredUnderYourControlCondition.instance,
                        "as long as an artifact entered the battlefield under your control this turn, " +
                                "creatures you control get +2/+2"))
                        .addHint(new ConditionHint(ArtifactEnteredUnderYourControlCondition.instance)),
                new ArtifactEnteredControllerWatcher());

        // Battalion -- Whenever Sentinel Sarah Lyons and at least two other creatures attack,
        // Sentinel Sarah Lyons deals damage equal to the number of artifacts you control to target player.
        Ability ability = new BattalionAbility(new DamageTargetEffect(ArtifactYouControlCount.instance)
                .setText("{this} deals damage equal to the number of artifacts you control to target player"));
        ability.addTarget(new TargetPlayer());
        ability.addHint(ArtifactYouControlHint.instance);
        this.addAbility(ability);
    }

    private SentinelSarahLyons(final SentinelSarahLyons card) {
        super(card);
    }

    @Override
    public SentinelSarahLyons copy() {
        return new SentinelSarahLyons(this);
    }
}
