package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DaybreakChimera extends CardImpl {

    public DaybreakChimera(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");

        this.subtype.add(SubType.CHIMERA);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // This spell costs {X} less to cast, where X is your devotion to white.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SpellCostReductionSourceEffect(DevotionCount.W))
                .addHint(DevotionCount.W.getHint())
        );

        // Flying
        this.addAbility(FlyingAbility.getInstance());
    }

    private DaybreakChimera(final DaybreakChimera card) {
        super(card);
    }

    @Override
    public DaybreakChimera copy() {
        return new DaybreakChimera(this);
    }
}
