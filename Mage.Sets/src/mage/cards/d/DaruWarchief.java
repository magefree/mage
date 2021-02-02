package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
 * @author North
 */
public final class DaruWarchief extends CardImpl {

    private static final FilterCard filter = new FilterCard("Soldier spells");
    private static final FilterCreaturePermanent filterCreatures = new FilterCreaturePermanent("Soldier creatures");

    static {
        filter.add(SubType.SOLDIER.getPredicate());
        filterCreatures.add(SubType.SOLDIER.getPredicate());
    }

    public DaruWarchief(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Soldier spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SpellsCostReductionControllerEffect(filter, 1)));

        // Soldier creatures you control get +1/+2.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 2, Duration.WhileOnBattlefield, filterCreatures, false)));
    }

    private DaruWarchief(final DaruWarchief card) {
        super(card);
    }

    @Override
    public DaruWarchief copy() {
        return new DaruWarchief(this);
    }
}
