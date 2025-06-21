package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CopyTargetStackObjectEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author Loki
 */
public final class SigilTracer extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledCreaturePermanent(SubType.WIZARD, "untapped Wizards you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
    }

    public SigilTracer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {1}{U}, Tap two untapped Wizards you control: Copy target instant or sorcery spell. You may choose new targets for the copy.
        Ability ability = new SimpleActivatedAbility(new CopyTargetStackObjectEffect(), new ManaCostsImpl<>("{1}{U}"));
        ability.addTarget(new TargetSpell(StaticFilters.FILTER_SPELL_INSTANT_OR_SORCERY));
        ability.addCost(new TapTargetCost(2, filter));
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
