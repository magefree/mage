package mage.cards.u;

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
public final class UndeadWarchief extends CardImpl {

    private static final FilterCard filter = new FilterCard("Zombie spells");
    private static final FilterCreaturePermanent filterCreatures = new FilterCreaturePermanent("Zombie creatures");

    static {
        filter.add(SubType.ZOMBIE.getPredicate());
        filterCreatures.add(SubType.ZOMBIE.getPredicate());
    }

    public UndeadWarchief(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Zombie spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SpellsCostReductionControllerEffect(filter, 1)));

        // Zombie creatures you control get +2/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(2, 1, Duration.WhileOnBattlefield, filterCreatures, false)));
    }

    private UndeadWarchief(final UndeadWarchief card) {
        super(card);
    }

    @Override
    public UndeadWarchief copy() {
        return new UndeadWarchief(this);
    }
}
