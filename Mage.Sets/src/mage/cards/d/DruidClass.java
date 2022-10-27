package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesClassLevelTriggeredAbility;
import mage.abilities.common.LandfallAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.LandsYouControlCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.effects.common.continuous.GainClassAbilitySourceEffect;
import mage.abilities.effects.common.continuous.PlayAdditionalLandsControllerEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.keyword.ClassLevelAbility;
import mage.abilities.keyword.ClassReminderAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubLayer;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.TokenImpl;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DruidClass extends CardImpl {

    public DruidClass(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");

        this.subtype.add(SubType.CLASS);

        // (Gain the next level as a sorcery to add its ability.)
        this.addAbility(new ClassReminderAbility());

        // Whenever a land enters the battlefield under your control, you gain 1 life.
        this.addAbility(new LandfallAbility(new GainLifeEffect(1)));

        // {2}{G}: Level 2
        this.addAbility(new ClassLevelAbility(2, "{2}{G}"));

        // You may play an additional land on each of your turns.
        this.addAbility(new SimpleStaticAbility(new GainClassAbilitySourceEffect(
                new PlayAdditionalLandsControllerEffect(1, Duration.WhileOnBattlefield), 2
        )));

        // {4}{G}: Level 3
        this.addAbility(new ClassLevelAbility(3, "{4}{G}"));

        // When this Class becomes level 3, target land you control becomes a creature with haste and "This creature's power and toughness are each equal to the number of lands you control." It's still a land.
        Ability ability = new BecomesClassLevelTriggeredAbility(new BecomesCreatureTargetEffect(
                new DruidClassToken(), false, true, Duration.Custom
        ), 3);
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND));
        this.addAbility(ability);
    }

    private DruidClass(final DruidClass card) {
        super(card);
    }

    @Override
    public DruidClass copy() {
        return new DruidClass(this);
    }
}

class DruidClassToken extends TokenImpl {

    DruidClassToken() {
        super("", "creature with haste and \"This creature's power and toughness are each equal to the number of lands you control.\"");
        this.cardType.add(CardType.CREATURE);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        this.addAbility(HasteAbility.getInstance());
        this.addAbility(new SimpleStaticAbility(new SetBasePowerToughnessSourceEffect(
                LandsYouControlCount.instance, Duration.EndOfGame, SubLayer.SetPT_7b
        ).setText("this creature's power and toughness are each equal to the number of lands you control")));
    }

    private DruidClassToken(final DruidClassToken token) {
        super(token);
    }

    public DruidClassToken copy() {
        return new DruidClassToken(this);
    }
}
