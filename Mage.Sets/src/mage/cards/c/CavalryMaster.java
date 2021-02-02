
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.FlankingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;

/**
 *
 * @author Plopman
 */
public final class CavalryMaster extends CardImpl {

    static final private FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("creatures you control with flanking");

    static {
        filter.add(new AbilityPredicate(FlankingAbility.class));
    }

    public CavalryMaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flanking
        this.addAbility(new FlankingAbility());
        // Other creatures you control with flanking have flanking.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD, new GainAbilityAllEffect(new FlankingAbility(), Duration.WhileOnBattlefield, filter, true)
                        .setText("Other creatures you control with flanking have flanking.")
        ));
    }

    private CavalryMaster(final CavalryMaster card) {
        super(card);
    }

    @Override
    public CavalryMaster copy() {
        return new CavalryMaster(this);
    }
}
