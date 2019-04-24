

package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.SetPowerToughnessSourceEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author Loki
 */
public final class DungroveElder extends CardImpl {

    final static FilterControlledPermanent filterLands = new FilterControlledPermanent("Forests you control");

        static {
            filterLands.add(new SubtypePredicate(SubType.FOREST));
        }

        public DungroveElder (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.TREEFOLK);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

            // Hexproof (This creature can't be the target of spells or abilities your opponents control.)
            this.addAbility(HexproofAbility.getInstance());

            // Dungrove Elder's power and toughness are each equal to the number of Forests you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetPowerToughnessSourceEffect(new PermanentsOnBattlefieldCount(filterLands), Duration.EndOfGame)));
    }

    public DungroveElder (final DungroveElder card) {
        super(card);
    }

    @Override
    public DungroveElder copy() {
        return new DungroveElder(this);
    }

}
