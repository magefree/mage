
package mage.cards.v;

import mage.MageInt;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.meta.OrTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.game.permanent.token.VoiceOfResurgenceToken;

import java.util.UUID;

/**
 *
 * @author jeffwadsworth
 */
public final class VoiceOfResurgence extends CardImpl {

    public VoiceOfResurgence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{W}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever an opponent casts a spell during your turn or when Voice of Resurgence dies, create a green and white Elemental creature token with "This creature's power and toughness are each equal to the number of creatures you control."
        OrTriggeredAbility ability = new OrTriggeredAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new VoiceOfResurgenceToken()),
                new ConditionalTriggeredAbility(
                        new SpellCastOpponentTriggeredAbility(null, new FilterSpell("a spell"), false),
                        MyTurnCondition.instance,
                        "Whenever an opponent casts a spell during your turn, "),
                new DiesTriggeredAbility(null, false));
        ability.setLeavesTheBattlefieldTrigger(true);
        this.addAbility(ability);

    }

    public VoiceOfResurgence(final VoiceOfResurgence card) {
        super(card);
    }

    @Override
    public VoiceOfResurgence copy() {
        return new VoiceOfResurgence(this);
    }

}

