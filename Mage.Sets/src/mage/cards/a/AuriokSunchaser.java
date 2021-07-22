package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MetalcraftCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.hint.common.MetalcraftHint;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author maurer.it_at_gmail.com
 */
public final class AuriokSunchaser extends CardImpl {

    public AuriokSunchaser(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(2, 2, Duration.WhileOnBattlefield),
                MetalcraftCondition.instance, "as long as you control three or more artifacts, {this} gets +2/+2"
        ));
        ability.addEffect(new ConditionalContinuousEffect(new GainAbilitySourceEffect(
                FlyingAbility.getInstance(), Duration.WhileOnBattlefield
        ), MetalcraftCondition.instance, "and has flying"));
        this.addAbility(ability.setAbilityWord(AbilityWord.METALCRAFT).addHint(MetalcraftHint.instance));
    }

    private AuriokSunchaser(final AuriokSunchaser card) {
        super(card);
    }

    @Override
    public AuriokSunchaser copy() {
        return new AuriokSunchaser(this);
    }
}
