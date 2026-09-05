package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.OneOrMoreCombatDamagePlayerTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.ForageCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.ConjureCardEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;


/**
 *
 * @author muz
 */
public final class EuruAcornScrounger extends CardImpl {

    public static final FilterPermanent filterSquirrels = new FilterControlledPermanent(SubType.SQUIRREL, "Squirrels you control");
    public static final FilterPermanent filterChitterspitter = new FilterControlledPermanent("permanent you control named Chitterspitter");
    public static final FilterPermanent filterToken = new FilterControlledPermanent("a token");

    static {
        filterChitterspitter.add(new NamePredicate("Chitterspitter"));
        filterToken.add(TokenPredicate.TRUE);
    }


    public EuruAcornScrounger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SQUIRREL);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Euru, Acorn Scrounger enters, you may forage. When you do, conjure a card named Chitterspitter onto the battlefield.
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
            new ConjureCardEffect("Chitterspitter", Zone.BATTLEFIELD, 1),
            false
        );
        this.addAbility(new EntersBattlefieldTriggeredAbility(
            new DoWhenCostPaid(ability, new ForageCost(), "Forage?")
        ));

        // Whenever one or more Squirrels you control deal combat damage to a player, you may sacrifice a token. If you do, put an acorn counter on each permanent you control named Chitterspitter.
        this.addAbility(new OneOrMoreCombatDamagePlayerTriggeredAbility(
            Zone.BATTLEFIELD,
            new DoIfCostPaid(
                new AddCountersAllEffect(CounterType.ACORN.createInstance(), filterChitterspitter),
                new SacrificeTargetCost(filterToken),
                "Sacrifice a token?"
            ),
            filterSquirrels,
            SetTargetPointer.NONE,
            false
        ));
    }

    private EuruAcornScrounger(final EuruAcornScrounger card) {
        super(card);
    }

    @Override
    public EuruAcornScrounger copy() {
        return new EuruAcornScrounger(this);
    }
}
