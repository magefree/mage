

package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;

/**
 * @author Loki
 */
public final class LordOfTheUnreal extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Illusion creatures");

    static {
        filter.add(SubType.ILLUSION.getPredicate());
    }

    public LordOfTheUnreal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{U}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Illusion creatures you control get +1/+1 and have hexproof
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filter, false));
        ability.addEffect(new GainAbilityControlledEffect(HexproofAbility.getInstance(), Duration.WhileOnBattlefield, filter, false)
                .setText("and have hexproof"));
        this.addAbility(ability);
    }

    private LordOfTheUnreal(final LordOfTheUnreal card) {
        super(card);
    }

    @Override
    public LordOfTheUnreal copy() {
        return new LordOfTheUnreal(this);
    }

}
