package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ScarbladeScout extends CardImpl {

    public ScarbladeScout(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // When this creature enters, mill two cards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new MillCardsControllerEffect(2)));
    }

    private ScarbladeScout(final ScarbladeScout card) {
        super(card);
    }

    @Override
    public ScarbladeScout copy() {
        return new ScarbladeScout(this);
    }
}
