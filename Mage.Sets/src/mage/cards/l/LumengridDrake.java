package mage.cards.l;

import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.MetalcraftCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.hint.common.MetalcraftHint;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author ayrat
 */
public final class LumengridDrake extends CardImpl {

    private static final String ruleText = "When {this} enters the battlefield, if you control three or more artifacts, return target creature to its owner's hand.";

    public LumengridDrake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");
        this.subtype.add(SubType.DRAKE);

        this.color.setBlue(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Metalcraft — When Lumengrid Drake enters the battlefield, if you control three or more artifacts, return target creature to its owner's hand.
        TriggeredAbility conditional = new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new ReturnToHandTargetEffect()), MetalcraftCondition.instance, ruleText);
        conditional.addTarget(new TargetCreaturePermanent());
        conditional.setAbilityWord(AbilityWord.METALCRAFT);
        conditional.addHint(MetalcraftHint.instance);
        this.addAbility(conditional);
    }

    private LumengridDrake(final LumengridDrake card) {
        super(card);
    }

    @Override
    public LumengridDrake copy() {
        return new LumengridDrake(this);
    }
}
