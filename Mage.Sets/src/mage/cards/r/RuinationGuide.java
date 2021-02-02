
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.abilities.keyword.IngestAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorlessPredicate;

/**
 *
 * @author LevelX2
 */
public final class RuinationGuide extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("colorless creatures");

    static {
        filter.add(ColorlessPredicate.instance);
    }

    public RuinationGuide(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.DRONE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Devoid
        this.addAbility(new DevoidAbility(this.color));
        // Ingest
        this.addAbility(new IngestAbility());
        // Other colorless creatures you control get +1/+0.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 0, Duration.WhileOnBattlefield, filter, true)));
    }

    private RuinationGuide(final RuinationGuide card) {
        super(card);
    }

    @Override
    public RuinationGuide copy() {
        return new RuinationGuide(this);
    }
}
