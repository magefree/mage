package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.keyword.EmpowerJaceEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class KeeperOfTheQuietHour extends CardImpl {

    public KeeperOfTheQuietHour(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");

        this.subtype.add(SubType.CHIMERA);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When this creature enters, empower Jace 2.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new EmpowerJaceEffect(2)));
    }

    private KeeperOfTheQuietHour(final KeeperOfTheQuietHour card) {
        super(card);
    }

    @Override
    public KeeperOfTheQuietHour copy() {
        return new KeeperOfTheQuietHour(this);
    }
}
