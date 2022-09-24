
package mage.cards.v;

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
import mage.target.common.TargetArtifactPermanent;

/**
 *
 * @author FenrisulfrX
 */
public final class VerduranEmissary extends CardImpl {

    public VerduranEmissary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Kicker {1}{R}
        this.addAbility(new KickerAbility("{1}{R}"));

        // When {this} enters the battlefield, if it was kicked, destroy target artifact. It can't be regenerated.
        TriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect(true));
        ability.addTarget(new TargetArtifactPermanent());
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, KickedCondition.ONCE,
                "When {this} enters the battlefield, if it was kicked, destroy target artifact. It can't be regenerated."));
    }

    private VerduranEmissary(final VerduranEmissary card) {
        super(card);
    }

    @Override
    public VerduranEmissary copy() {
        return new VerduranEmissary(this);
    }
}
