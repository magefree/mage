package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class PrimaryUnimatrixAdjunct extends CardImpl {

    public PrimaryUnimatrixAdjunct(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.BORG);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Artifact creatures you control have double strike.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
            DoubleStrikeAbility.getInstance(), Duration.WhileOnBattlefield,
            StaticFilters.FILTER_PERMANENTS_ARTIFACT_CREATURE
        )));
    }

    private PrimaryUnimatrixAdjunct(final PrimaryUnimatrixAdjunct card) {
        super(card);
    }

    @Override
    public PrimaryUnimatrixAdjunct copy() {
        return new PrimaryUnimatrixAdjunct(this);
    }
}
