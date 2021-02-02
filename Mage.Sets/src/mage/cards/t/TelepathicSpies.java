
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.LookAtTargetPlayerHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LoneFox

 */
public final class TelepathicSpies extends CardImpl {

    public TelepathicSpies(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Telepathic Spies enters the battlefield, look at target opponent's hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new LookAtTargetPlayerHandEffect(), false);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private TelepathicSpies(final TelepathicSpies card) {
        super(card);
    }

    @Override
    public TelepathicSpies copy() {
        return new TelepathicSpies(this);
    }
}
