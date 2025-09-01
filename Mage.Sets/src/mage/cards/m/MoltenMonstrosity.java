package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.GreatestAmongPermanentsValue;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MoltenMonstrosity extends CardImpl {

    public MoltenMonstrosity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{7}{R}");

        this.subtype.add(SubType.HELLION);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // This spell costs {X} less to cast, where X is the greatest power among creatures you control.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionSourceEffect(GreatestAmongPermanentsValue.POWER_CONTROLLED_CREATURES)
        ).setRuleAtTheTop(true).addHint(GreatestAmongPermanentsValue.POWER_CONTROLLED_CREATURES.getHint()));

        // Trample
        this.addAbility(TrampleAbility.getInstance());
    }

    private MoltenMonstrosity(final MoltenMonstrosity card) {
        super(card);
    }

    @Override
    public MoltenMonstrosity copy() {
        return new MoltenMonstrosity(this);
    }
}
