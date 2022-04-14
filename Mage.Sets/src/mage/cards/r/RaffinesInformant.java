package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.keyword.ConniveSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RaffinesInformant extends CardImpl {

    public RaffinesInformant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When Raffine's Informant enters the battlefield, it connives.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ConniveSourceEffect()));
    }

    private RaffinesInformant(final RaffinesInformant card) {
        super(card);
    }

    @Override
    public RaffinesInformant copy() {
        return new RaffinesInformant(this);
    }
}
