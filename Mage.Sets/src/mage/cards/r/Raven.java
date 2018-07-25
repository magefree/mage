package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author anonymous
 */
public final class Raven extends CardImpl {

    public Raven(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{W}{W}");
        
        this.subtype.add(SubType.TERRAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever a permanent you control is turned face down, you gain 1 life.
        // {3}, {T}: Turn target permanent you control face down.
    }

    public Raven(final Raven card) {
        super(card);
    }

    @Override
    public Raven copy() {
        return new Raven(this);
    }
}
