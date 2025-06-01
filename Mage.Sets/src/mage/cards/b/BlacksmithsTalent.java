package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.AttachTargetToTargetEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.continuous.GainClassAbilitySourceEffect;
import mage.abilities.keyword.ClassLevelAbility;
import mage.abilities.keyword.ClassReminderAbility;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.EquippedPredicate;
import mage.game.permanent.token.SwordToken;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BlacksmithsTalent extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(EquippedPredicate.instance);
    }

    public BlacksmithsTalent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{R}");

        this.subtype.add(SubType.CLASS);

        // (Gain the next level as a sorcery to add its ability.)
        this.addAbility(new ClassReminderAbility());

        // When Blacksmith's Talent enters, create a colorless Equipment artifact token named Sword with "Equipped creature gets +1/+1" and equip {2}.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new SwordToken())));

        // {2}{R}: Level 2
        this.addAbility(new ClassLevelAbility(2, "{2}{R}"));

        // At the beginning of combat on your turn, attach target Equipment you control to up to one target creature you control.
        Ability ability = new BeginningOfCombatTriggeredAbility(new AttachTargetToTargetEffect());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_EQUIPMENT));
        ability.addTarget(new TargetControlledCreaturePermanent(0, 1));
        this.addAbility(new SimpleStaticAbility(new GainClassAbilitySourceEffect(ability, 2)));

        // {3}{R}: Level 3
        this.addAbility(new ClassLevelAbility(3, "{3}{R}"));

        // During your turn, equipped creatures you control have double strike and haste.
        ability = new SimpleStaticAbility(new ConditionalContinuousEffect(new GainAbilityControlledEffect(
                DoubleStrikeAbility.getInstance(), Duration.WhileOnBattlefield, filter
        ), MyTurnCondition.instance, "during your turn, equipped creatures you control have double strike"));
        ability.addEffect(new ConditionalContinuousEffect(new GainAbilityControlledEffect(
                HasteAbility.getInstance(), Duration.WhileOnBattlefield, filter
        ), MyTurnCondition.instance, "and haste"));
        this.addAbility(new SimpleStaticAbility(new GainClassAbilitySourceEffect(ability, 3)));
    }

    private BlacksmithsTalent(final BlacksmithsTalent card) {
        super(card);
    }

    @Override
    public BlacksmithsTalent copy() {
        return new BlacksmithsTalent(this);
    }
}
