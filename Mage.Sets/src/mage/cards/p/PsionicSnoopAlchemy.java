package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.keyword.ConniveSourceEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 * @author chesse20
 */
public final class PsionicSnoopAlchemy extends CardImpl {

    public PsionicSnoopAlchemy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4); //buffed toughness

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Psionic Snoop enters the battlefield, it connives.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ConniveSourceEffect()));
    }

    private PsionicSnoopAlchemy(final PsionicSnoopAlchemy card) {
        super(card);
    }

    @Override
    public PsionicSnoopAlchemy copy() {
        return new PsionicSnoopAlchemy(this);
    }
}
