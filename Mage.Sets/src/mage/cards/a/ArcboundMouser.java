package mage.cards.a;

import mage.MageInt;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.ModularAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArcboundMouser extends CardImpl {

    public ArcboundMouser(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.CAT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Modular 1
        this.addAbility(new ModularAbility(this, 1));
    }

    private ArcboundMouser(final ArcboundMouser card) {
        super(card);
    }

    @Override
    public ArcboundMouser copy() {
        return new ArcboundMouser(this);
    }
}
