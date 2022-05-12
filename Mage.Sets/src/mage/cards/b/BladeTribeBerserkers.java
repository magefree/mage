package mage.cards.b;

import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.MetalcraftCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.hint.common.MetalcraftHint;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author North
 */
public final class BladeTribeBerserkers extends CardImpl {

    private static final String effectText = "When {this} enters the battlefield, if you control three or more artifacts, {this} gets +3/+3 and gains haste until end of turn.";

    public BladeTribeBerserkers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.subtype.add(SubType.HUMAN, SubType.BERSERKER);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        //<i>Metalcraft</i> &mdash; When Blade-Tribe Berserkers enters the battlefield, if you control three or more artifacts, Blade-Tribe Berserkers gets +3/+3 and gains haste until end of turn.
        TriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new BoostSourceEffect(3, 3, Duration.EndOfTurn), false);
        ability.addEffect(new GainAbilitySourceEffect(HasteAbility.getInstance(), Duration.EndOfTurn));
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, MetalcraftCondition.instance, effectText)
                .setAbilityWord(AbilityWord.METALCRAFT)
                .addHint(MetalcraftHint.instance)
        );
    }

    private BladeTribeBerserkers(final BladeTribeBerserkers card) {
        super(card);
    }

    @Override
    public BladeTribeBerserkers copy() {
        return new BladeTribeBerserkers(this);
    }
}
