package mage.cards.e;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.TotalPermanentsManaValue;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.HistoricPredicate;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author grimreap124
 */
public final class ExcaliburSwordOfEden extends CardImpl {

    private static final FilterControlledCreaturePermanent filterLegendary
            = new FilterControlledCreaturePermanent("legendary creature");
    private static final FilterControlledPermanent historicFilter = new FilterControlledPermanent("historic permanents you control");

    static {
        filterLegendary.add(SuperType.LEGENDARY.getPredicate());
        historicFilter.add(HistoricPredicate.instance);
    }
    
    private static final TotalPermanentsManaValue xValue = new TotalPermanentsManaValue(historicFilter);

    public ExcaliburSwordOfEden(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{12}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.EQUIPMENT);

        // This spell costs {X} less to cast, where X is the total mana value of historic permanents you control.
        this.addAbility(new SimpleStaticAbility(
                        Zone.ALL,
                        new SpellCostReductionSourceEffect(xValue)
                ).addHint(xValue.getHint()).setRuleAtTheTop(true)
        );

        // Equipped creature gets +10/+0 and has vigilance.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(10, 0));
        ability.addEffect(new GainAbilityAttachedEffect(VigilanceAbility.getInstance(), AttachmentType.EQUIPMENT).setText("and has vigilance"));
        this.addAbility(ability);

        // Equip legendary creature {2}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(2), new TargetControlledCreaturePermanent(filterLegendary), false));

    }

    private ExcaliburSwordOfEden(final ExcaliburSwordOfEden card) {
        super(card);
    }

    @Override
    public ExcaliburSwordOfEden copy() {
        return new ExcaliburSwordOfEden(this);
    }
}
