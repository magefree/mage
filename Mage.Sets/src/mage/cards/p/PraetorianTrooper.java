package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author NinthWorld
 */
public final class PraetorianTrooper extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Trooper creatures");

    static {
        filter.add(SubType.TROOPER.getPredicate());
    }

    public PraetorianTrooper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.TROOPER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Trooper creatures you control have double strike.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityControlledEffect(DoubleStrikeAbility.getInstance(), Duration.WhileOnBattlefield, filter, false)));
    }

    private PraetorianTrooper(final PraetorianTrooper card) {
        super(card);
    }

    @Override
    public PraetorianTrooper copy() {
        return new PraetorianTrooper(this);
    }
}
