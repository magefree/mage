
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.MorbidCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.common.MorbidHint;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.SpiritWhiteToken;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class EmissaryOfTheSleepless extends CardImpl {

    public EmissaryOfTheSleepless(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}");
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // When Emissary of the Sleepless enters the battlefield, if a creature died this turn, create a 1/1 white Spirit creature token with flying.
        TriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new SpiritWhiteToken()));
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, MorbidCondition.instance, "When {this} enters the battlefield, if a creature died this turn, create a 1/1 white Spirit creature token with flying.").addHint(MorbidHint.instance));
    }

    private EmissaryOfTheSleepless(final EmissaryOfTheSleepless card) {
        super(card);
    }

    @Override
    public EmissaryOfTheSleepless copy() {
        return new EmissaryOfTheSleepless(this);
    }
}
