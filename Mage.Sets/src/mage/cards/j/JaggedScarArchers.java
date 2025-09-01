package mage.cards.j;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class JaggedScarArchers extends CardImpl {

    private static final FilterControlledPermanent controlledElvesFilter = new FilterControlledPermanent("Elves you control");

    static {
        controlledElvesFilter.add(SubType.ELF.getPredicate());
    }

    public JaggedScarArchers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.ARCHER);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Jagged-Scar Archers's power and toughness are each equal to the number of Elves you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerToughnessSourceEffect(new PermanentsOnBattlefieldCount(controlledElvesFilter))));

        // {tap}: Jagged-Scar Archers deals damage equal to its power to target creature with flying.
        Ability ability = new SimpleActivatedAbility(new DamageTargetEffect(SourcePermanentPowerValue.NOT_NEGATIVE).setText("{this} deals damage equal to its power to target creature with flying"), new TapSourceCost());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_FLYING));
        this.addAbility(ability);
    }

    private JaggedScarArchers(final JaggedScarArchers card) {
        super(card);
    }

    @Override
    public JaggedScarArchers copy() {
        return new JaggedScarArchers(this);
    }
}
