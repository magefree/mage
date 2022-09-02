package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VodalianHexcatcher extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.MERFOLK, "Merfolk");
    private static final FilterControlledPermanent filter2 = new FilterControlledPermanent(SubType.MERFOLK);

    public VodalianHexcatcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Other Merfolk you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                1, 1, Duration.WhileOnBattlefield, filter, true
        )));

        // Sacrifice a Merfolk: Counter target noncreature spell unless its controller pays {1}.
        Ability ability = new SimpleActivatedAbility(
                new CounterUnlessPaysEffect(new GenericManaCost(1)), new SacrificeTargetCost(filter2)
        );
        ability.addTarget(new TargetSpell(StaticFilters.FILTER_SPELL_NON_CREATURE));
        this.addAbility(ability);
    }

    private VodalianHexcatcher(final VodalianHexcatcher card) {
        super(card);
    }

    @Override
    public VodalianHexcatcher copy() {
        return new VodalianHexcatcher(this);
    }
}
