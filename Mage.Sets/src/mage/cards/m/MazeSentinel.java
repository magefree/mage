

package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.MulticoloredPredicate;

/**
 *
 * @author LevelX2
 */


public final class MazeSentinel extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("Multicolored creatures you control");
    static {
        filter.add(MulticoloredPredicate.instance);
    }

    public MazeSentinel (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{W}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(3);
        this.toughness = new MageInt(6);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Multicolored creatures you control have vigilance.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(VigilanceAbility.getInstance(), Duration.WhileOnBattlefield, filter)));

    }

    private MazeSentinel(final MazeSentinel card) {
        super(card);
    }

    @Override
    public MazeSentinel copy() {
        return new MazeSentinel(this);
    }

}
