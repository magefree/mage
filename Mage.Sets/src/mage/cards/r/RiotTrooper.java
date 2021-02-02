package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.CanBlockAdditionalCreatureAllEffect;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;

/**
 *
 * @author NinthWorld
 */
public final class RiotTrooper extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("trooper creatures you control");

    static {
        filter.add(SubType.TROOPER.getPredicate());
    }

    public RiotTrooper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.TROOPER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Each trooper creature you control can block an additional creature each combat.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new CanBlockAdditionalCreatureAllEffect(1, filter, Duration.WhileOnBattlefield)
                    .setText("Each trooper creature you control can block an additional creature each combat")));
    }

    private RiotTrooper(final RiotTrooper card) {
        super(card);
    }

    @Override
    public RiotTrooper copy() {
        return new RiotTrooper(this);
    }
}
