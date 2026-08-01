package mage.cards.e;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.target.TargetPermanent;

/**
 *
 * @author muz
 */
public final class ElvenkingsHalls extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.ELF);

    public ElvenkingsHalls(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // This land enters tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {G} or {U}.
        this.addAbility(new GreenManaAbility());
        this.addAbility(new BlueManaAbility());

        // {2}{G}{U}, {T}, Sacrifice this land: Put two +1/+1 counters on target Elf you control. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
            new AddCountersTargetEffect(CounterType.P1P1.createInstance(2)),
            new ManaCostsImpl<>("{2}{G}{U}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private ElvenkingsHalls(final ElvenkingsHalls card) {
        super(card);
    }

    @Override
    public ElvenkingsHalls copy() {
        return new ElvenkingsHalls(this);
    }
}
