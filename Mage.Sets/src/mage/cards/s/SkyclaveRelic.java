package mage.cards.s;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.CreateTokenCopySourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.KickerAbility;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SkyclaveRelic extends CardImpl {

    public SkyclaveRelic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // Kicker {3}
        this.addAbility(new KickerAbility("{3}"));

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());

        // When Skyclave Relic enters the battlefield, if it was kicked, create two tapped tokens that are copies of Skyclave Relic.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new CreateTokenCopySourceEffect(2, true)),
                KickedCondition.ONCE, "When {this} enters the battlefield, if it was kicked, " +
                "create two tapped tokens that are copies of {this}."
        ));

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());
    }

    private SkyclaveRelic(final SkyclaveRelic card) {
        super(card);
    }

    @Override
    public SkyclaveRelic copy() {
        return new SkyclaveRelic(this);
    }
}
