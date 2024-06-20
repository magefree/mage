package mage.cards.e;

import java.util.*;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.MillThenPutInHandEffect;
import mage.abilities.effects.common.MillThenPutOntoBattlefieldEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;

/**
 *
 * @author grimreap124
 */
public final class EivorWolfKissed extends CardImpl {

    private static final FilterCard sagaFilter = new FilterCard("Saga card");
    private static final List<FilterCard> filters = new ArrayList<>();
    static {
        sagaFilter.add(Predicates.or( SubType.SAGA.getPredicate()));
        filters.add(sagaFilter);
        filters.add(StaticFilters.FILTER_CARD_LAND);
    }

    public EivorWolfKissed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{G}{W}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ASSASSIN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(7);
        this.toughness = new MageInt(6);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Eivor, Wolf-Kissed deals combat damage to a player, you mill that many cards. You may put a Saga card and/or a land card from among them onto the battlefield.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(new MillThenPutOntoBattlefieldEffect(SavedDamageValue.MANY, filters, null, true, 1)
                .setText("you mill that many cards. You may put a Saga card and/or a land card from among them onto the battlefield."));
        this.addAbility(ability);
    }

    private EivorWolfKissed(final EivorWolfKissed card) {
        super(card);
    }

    @Override
    public EivorWolfKissed copy() {
        return new EivorWolfKissed(this);
    }
}
