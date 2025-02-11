package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryGraveyardPutInHandEffect;
import mage.abilities.keyword.CascadeAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.CardType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

/**
 *
 * @author padfoot
 */
public final class TheFirstDoctor extends CardImpl {

    private static final FilterSpell filterSpell = new FilterSpell("a spell with cascade");
    private static final FilterCard filterTARDIS = new FilterCard("TARDIS");

    static {
        filterSpell.add(new AbilityPredicate(CascadeAbility.class));
	filterTARDIS.add(new NamePredicate("TARDIS"));
    }

    public TheFirstDoctor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{U}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TIME_LORD, SubType.DOCTOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When The First Doctor enters the battlefield, search your library and/or graveyard for a card named TARDIS, reveal it, and put it into your hand. If you search your library this way, shuffle.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryGraveyardPutInHandEffect(filterTARDIS), false));

	// Whenever you cast a spell with cascade, put a +1/+1 counter on target artifact or creature.
        Ability ability = new SpellCastControllerTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()),
		filterSpell,
		false
	);
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_CREATURE));
        this.addAbility(ability);
    }

    private TheFirstDoctor(final TheFirstDoctor card) {
        super(card);
    }

    @Override
    public TheFirstDoctor copy() {
        return new TheFirstDoctor(this);
    }
}
