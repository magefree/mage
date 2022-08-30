
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author LoneFox

 */
public final class BenalishEmissary extends CardImpl {

    public BenalishEmissary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.HUMAN, SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Kicker {1}{G}
        this.addAbility(new KickerAbility("{1}{G}"));
        // When Benalish Emissary enters the battlefield, if it was kicked, destroy target land.
        TriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect());
        ability.addTarget(new TargetLandPermanent());
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, KickedCondition.ONCE,
            "When {this} enters the battlefield, if it was kicked, destroy target land."));
    }

    private BenalishEmissary(final BenalishEmissary card) {
        super(card);
    }

    @Override
    public BenalishEmissary copy() {
        return new BenalishEmissary(this);
    }
}
