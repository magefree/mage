
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.TargetOfOpponentsSpellOrAbilityTriggeredAbility;
import mage.abilities.condition.common.EnchantedSourceCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

/**
 *
 * @author emerald000
 */
public final class RayneAcademyChancellor extends CardImpl {

    public RayneAcademyChancellor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever you or a permanent you control becomes the target of a spell or ability an opponent controls, you may draw a card.
        // You may draw an additional card if Rayne, Academy Chancellor is enchanted.
        Effect drawEffect = new ConditionalOneShotEffect(
                new DrawCardSourceControllerEffect(2),
                new DrawCardSourceControllerEffect(1),
                new EnchantedSourceCondition(),
                "you may draw a card. You may draw an additional card if {this} is enchanted"
        );
        this.addAbility(new TargetOfOpponentsSpellOrAbilityTriggeredAbility(drawEffect));
    }

    private RayneAcademyChancellor(final RayneAcademyChancellor card) {
        super(card);
    }

    @Override
    public RayneAcademyChancellor copy() {
        return new RayneAcademyChancellor(this);
    }
}
