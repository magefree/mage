package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.keyword.DiscoverEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PrimordialGnawer extends CardImpl {

    public PrimordialGnawer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.subtype.add(SubType.INSECT);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(2);

        // When Primordial Gnawer dies, discover 3.
        this.addAbility(new DiesSourceTriggeredAbility(new DiscoverEffect(3)));
    }

    private PrimordialGnawer(final PrimordialGnawer card) {
        super(card);
    }

    @Override
    public PrimordialGnawer copy() {
        return new PrimordialGnawer(this);
    }
}
