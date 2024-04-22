
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;


/**
 *
 * @author Rystan
 */
public final class ArvadTheCursed extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("legendary creatures");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    public ArvadTheCursed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE, SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());
        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Other legendary creatures you control get +2/+2.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(2, 2, Duration.WhileOnBattlefield, filter, true)));
    }

    private ArvadTheCursed(final ArvadTheCursed card) {
        super(card);
    }

    @Override
    public ArvadTheCursed copy() {
        return new ArvadTheCursed(this);
    }
}
