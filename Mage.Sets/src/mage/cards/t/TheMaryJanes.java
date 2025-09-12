package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.CreaturesThatAttackedThisTurnCount;
import mage.abilities.effects.common.cost.SpellCostReductionForEachSourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheMaryJanes extends CardImpl {

    public TheMaryJanes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.BARD);
        this.subtype.add(SubType.PERFORMER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // This spell costs {1} less to cast for each creature that attacked this turn.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL,
                new SpellCostReductionForEachSourceEffect(
                        1, CreaturesThatAttackedThisTurnCount.instance
                )
        ).addHint(CreaturesThatAttackedThisTurnCount.getHint()));

        // Menace
        this.addAbility(new MenaceAbility());
    }

    private TheMaryJanes(final TheMaryJanes card) {
        super(card);
    }

    @Override
    public TheMaryJanes copy() {
        return new TheMaryJanes(this);
    }
}
