
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.SetBaseToughnessSourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;

/**
 *
 * @author Loki
 */
public final class TraprootKami extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Forests on the battlefield");

    static {
        filter.add(SubType.FOREST.getPredicate());
    }

    public TraprootKami(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);
        // Defender; reach
        this.addAbility(DefenderAbility.getInstance());
        this.addAbility(ReachAbility.getInstance());
        // Traproot Kami's toughness is equal to the number of Forests on the battlefield.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBaseToughnessSourceEffect(new PermanentsOnBattlefieldCount(filter))));
    }

    private TraprootKami(final TraprootKami card) {
        super(card);
    }

    @Override
    public TraprootKami copy() {
        return new TraprootKami(this);
    }
}
