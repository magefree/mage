
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.TargetSpell;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 * @author Loki
 */
public final class SigilTracer extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("untapped Wizards you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
        filter.add(SubType.WIZARD.getPredicate());
    }

    public SigilTracer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}{U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {1}{U}, Tap two untapped Wizards you control: Copy target instant or sorcery spell. You may choose new targets for the copy.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CopyTargetSpellEffect(), new ManaCostsImpl<>("{1}{U}"));
        ability.addTarget(new TargetSpell(StaticFilters.FILTER_SPELL_INSTANT_OR_SORCERY));
        ability.addCost(new TapTargetCost(new TargetControlledCreaturePermanent(2, 2, filter, false)));
        this.addAbility(ability);
    }

    private SigilTracer(final SigilTracer card) {
        super(card);
    }

    @Override
    public SigilTracer copy() {
        return new SigilTracer(this);
    }
}
