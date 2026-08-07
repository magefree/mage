package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.MenaceAbility;
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
public final class OrganicAvulsionUnit extends CardImpl {

    public OrganicAvulsionUnit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.BORG);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // {2}: Artifact creatures you control gain menace until end of turn.
        this.addAbility(new SimpleActivatedAbility(
            new GainAbilityControlledEffect(
                new MenaceAbility(false), Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENTS_ARTIFACT_CREATURE
            ),
            new ManaCostsImpl<>("{2}"))
        );
    }

    private OrganicAvulsionUnit(final OrganicAvulsionUnit card) {
        super(card);
    }

    @Override
    public OrganicAvulsionUnit copy() {
        return new OrganicAvulsionUnit(this);
    }
}
