package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.CavesControlledAndInGraveCount;
import mage.abilities.effects.common.cost.SpellCostReductionForEachSourceEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GargantuanLeech extends CardImpl {

    public GargantuanLeech(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{7}{B}");

        this.subtype.add(SubType.LEECH);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // This spell costs {1} less to cast for each Cave you control and each Cave card in your graveyard.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL,
                new SpellCostReductionForEachSourceEffect(
                        1, CavesControlledAndInGraveCount.FOR_EACH
                )
        ).addHint(CavesControlledAndInGraveCount.getHint()));

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());
    }

    private GargantuanLeech(final GargantuanLeech card) {
        super(card);
    }

    @Override
    public GargantuanLeech copy() {
        return new GargantuanLeech(this);
    }
}
