package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.ForEachPlayerTargetsAdjuster;
import mage.target.targetpointer.EachTargetPointer;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class HaythamKenway extends CardImpl {

    private static final FilterCard filter = new FilterCard("Assassins");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent(SubType.KNIGHT, "Knights");

    static {
        filter.add(SubType.ASSASSIN.getPredicate());
    }

    public HaythamKenway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN, SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Protection from Assassins
        this.addAbility(new ProtectionAbility(filter));

        // Other Knights you control get +2/+2 and have protection from Assassins.
        Ability ability = new SimpleStaticAbility(new BoostControlledEffect(
                2, 2, Duration.WhileOnBattlefield, filter2, true
        ));
        ability.addEffect(new GainAbilityControlledEffect(
                new ProtectionAbility(filter), Duration.WhileOnBattlefield, filter2, true
        ).setText("and have protection from Assassins"));
        this.addAbility(ability);

        // When Haytham Kenway enters, for each opponent, exile up to one target creature that player controls until Haytham Kenway leaves the battlefield.
        Ability ability2 = new EntersBattlefieldTriggeredAbility(new ExileUntilSourceLeavesEffect()
                .setTargetPointer(new EachTargetPointer())
                .setText("for each opponent, exile up to one target creature that player controls until {this} leaves the battlefield")
        );
        ability2.addTarget(new TargetCreaturePermanent(0, 1));
        ability2.setTargetAdjuster(new ForEachPlayerTargetsAdjuster(false, true));
        this.addAbility(ability2);
    }

    private HaythamKenway(final HaythamKenway card) {
        super(card);
    }

    @Override
    public HaythamKenway copy() {
        return new HaythamKenway(this);
    }
}
