
package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPermanent;

/**
 *
 * @author LoneFox

 */
public final class UrborgEmissary extends CardImpl {

    public UrborgEmissary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Kicker {1}{U}
        this.addAbility(new KickerAbility("{1}{U}"));
        // When Urborg Emissary enters the battlefield, if it was kicked, return target permanent to its owner's hand.
        TriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new ReturnToHandTargetEffect());
        ability.addTarget(new TargetPermanent());
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, KickedCondition.ONCE,
            "When {this} enters the battlefield, if it was kicked, return target permanent to its owner's hand."));
    }

    private UrborgEmissary(final UrborgEmissary card) {
        super(card);
    }

    @Override
    public UrborgEmissary copy() {
        return new UrborgEmissary(this);
    }
}
