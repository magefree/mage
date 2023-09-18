package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
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
public final class ScuttlingSliver extends CardImpl {

    public ScuttlingSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.SLIVER);
        this.subtype.add(SubType.TRILOBITE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Sliver creatures you control have "{2}: Untap this creature."
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new SimpleActivatedAbility(
                        new UntapSourceEffect().setText("untap this creature"), new GenericManaCost(2)
                ), Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_SLIVERS)
                .withForceQuotes()
        ));
    }

    private ScuttlingSliver(final ScuttlingSliver card) {
        super(card);
    }

    @Override
    public ScuttlingSliver copy() {
        return new ScuttlingSliver(this);
    }
}
