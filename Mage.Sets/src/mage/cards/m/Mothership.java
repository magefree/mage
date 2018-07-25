package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author anonymous
 */
public final class Mothership extends CardImpl {

    public Mothership(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{W}{U}");
        
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.PROTOSS);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Spells your opponents cast cost {2} more to cast.
    }

    public Mothership(final Mothership card) {
        super(card);
    }

    @Override
    public Mothership copy() {
        return new Mothership(this);
    }
}
