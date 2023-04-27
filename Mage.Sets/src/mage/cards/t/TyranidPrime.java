package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.EvolveAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TyranidPrime extends CardImpl {

    public TyranidPrime(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{U}");

        this.subtype.add(SubType.TYRANID);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Evolve
        this.addAbility(new EvolveAbility());

        // Synapse Creature -- Other creatures you control have evolve.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new EvolveAbility(), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENT_CREATURES, true
        )).withFlavorWord("Synapse Creature"));
    }

    private TyranidPrime(final TyranidPrime card) {
        super(card);
    }

    @Override
    public TyranidPrime copy() {
        return new TyranidPrime(this);
    }
}
