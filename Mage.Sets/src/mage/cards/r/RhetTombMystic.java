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
 * @author sobiech
 */
public final class RhetTombMystic extends CardImpl {

    public RhetTombMystic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Each creature card in your hand has cycling {1}{U}.
    }

    private RhetTombMystic(final RhetTombMystic card) {
        super(card);
    }

    @Override
    public RhetTombMystic copy() {
        return new RhetTombMystic(this);
    }
}
