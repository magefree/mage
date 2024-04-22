package mage.cards.i;

import java.util.UUID;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.keyword.TheRingTemptsYouEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author TheElk801
 */
public final class InheritedEnvelope extends CardImpl {

    public InheritedEnvelope(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // When Inherited Envelope enters the battlefield, the Ring tempts you.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new TheRingTemptsYouEffect()));

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());
    }

    private InheritedEnvelope(final InheritedEnvelope card) {
        super(card);
    }

    @Override
    public InheritedEnvelope copy() {
        return new InheritedEnvelope(this);
    }
}
