package mage.cards.r;

import mage.MageInt;
import mage.abilities.keyword.PrototypeAbility;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RustGoliath extends CardImpl {

    public RustGoliath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{10}");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(10);
        this.toughness = new MageInt(10);

        // Prototype {3}{G}{G} -- 3/5
        this.addAbility(new PrototypeAbility(this, "{3}{G}{G}", 3, 5));

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());
    }

    private RustGoliath(final RustGoliath card) {
        super(card);
    }

    @Override
    public RustGoliath copy() {
        return new RustGoliath(this);
    }
}
