package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.keyword.EmpowerJaceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class ArcaneAmphisbaena extends CardImpl {

    public ArcaneAmphisbaena(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.SNAKE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // When this creature enters, empower Jace 2.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new EmpowerJaceEffect(2)));
    }

    private ArcaneAmphisbaena(final ArcaneAmphisbaena card) {
        super(card);
    }

    @Override
    public ArcaneAmphisbaena copy() {
        return new ArcaneAmphisbaena(this);
    }
}
