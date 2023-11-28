package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.AfflictAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class CybermanPatrol extends CardImpl {

    public CybermanPatrol(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");

        this.subtype.add(SubType.CYBERMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Artifact creatures you control have afflict 3.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new AfflictAbility(3), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENTS_ARTIFACT_CREATURE
        )));
    }

    private CybermanPatrol(final CybermanPatrol card) {
        super(card);
    }

    @Override
    public CybermanPatrol copy() {
        return new CybermanPatrol(this);
    }
}
