package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.common.delayed.CopyNextSpellDelayedTriggeredAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.CantBeBlockedSourceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MercurialSpelldancer extends CardImpl {

    public MercurialSpelldancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Mercurial Spelldancer can't be blocked.
        this.addAbility(new CantBeBlockedSourceAbility());

        // Whenever you cast a noncreature spell, put an oil counter on Mercurial Spelldancer.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new AddCountersSourceEffect(CounterType.OIL.createInstance()),
                StaticFilters.FILTER_SPELL_A_NON_CREATURE, false
        ));

        // Whenever Mercurial Spelldancer deals combat damage to a player, you may remove two oil counters from it. If you do, when you cast your next instant or sorcery spell this turn, copy that spell. You may choose new targets for the copy.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new DoIfCostPaid(
                        new CreateDelayedTriggeredAbilityEffect(new CopyNextSpellDelayedTriggeredAbility()),
                        new RemoveCountersSourceCost(CounterType.OIL.createInstance(2)).setText("remove two oil counters from it")
                ), false
        ));
    }

    private MercurialSpelldancer(final MercurialSpelldancer card) {
        super(card);
    }

    @Override
    public MercurialSpelldancer copy() {
        return new MercurialSpelldancer(this);
    }
}
