package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class Attercop extends CardImpl {

    public Attercop(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.SPIDER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Landfall -- Whenever a land you control enters, this creature gets +1/+1 until end of turn.
        this.addAbility(new LandfallAbility(new BoostSourceEffect(1, 1, Duration.EndOfTurn)));
    }

    private Attercop(final Attercop card) {
        super(card);
    }

    @Override
    public Attercop copy() {
        return new Attercop(this);
    }
}
