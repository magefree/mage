

package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.TrampleAbility;
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


public final class MazeBehemoth extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("Multicolored creatures you control");
    static {
        filter.add(MulticoloredPredicate.instance);
    }

    public MazeBehemoth (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{G}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Multicolored creatures you control have trample.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(TrampleAbility.getInstance(), Duration.WhileOnBattlefield, filter)));

    }

    private MazeBehemoth(final MazeBehemoth card) {
        super(card);
    }

    @Override
    public MazeBehemoth copy() {
        return new MazeBehemoth(this);
    }

}
