
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.PersistAbility;
import mage.abilities.keyword.WitherAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class RendclawTrow extends CardImpl {

    public RendclawTrow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B/G}");
        this.subtype.add(SubType.TROLL);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Wither
        this.addAbility(WitherAbility.getInstance());
        // Persist
        this.addAbility(new PersistAbility());
    }

    private RendclawTrow(final RendclawTrow card) {
        super(card);
    }

    @Override
    public RendclawTrow copy() {
        return new RendclawTrow(this);
    }
}
