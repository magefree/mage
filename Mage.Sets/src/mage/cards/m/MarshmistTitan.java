package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class MarshmistTitan extends CardImpl {

    public MarshmistTitan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{B}");
        this.subtype.add(SubType.GIANT);

        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // This spell costs {X} less to cast, where X is your devotion to black.
        Ability ability = new SimpleStaticAbility(Zone.ALL, new SpellCostReductionSourceEffect(DevotionCount.B));
        ability.addHint(DevotionCount.B.getHint());
        this.addAbility(ability);

    }

    private MarshmistTitan(final MarshmistTitan card) {
        super(card);
    }

    @Override
    public MarshmistTitan copy() {
        return new MarshmistTitan(this);
    }
}
