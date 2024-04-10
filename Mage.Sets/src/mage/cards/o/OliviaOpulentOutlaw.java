package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.DealCombatDamageControlledTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.OutlawPredicate;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class OliviaOpulentOutlaw extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(OutlawPredicate.instance);
    }

    private static final FilterControlledPermanent filterTreasure = new FilterControlledPermanent("a Treasure");

    static {
        filterTreasure.add(SubType.TREASURE.getPredicate());
    }

    public OliviaOpulentOutlaw(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Whenever one or more outlaws you control deal combat damage to a player, create a Treasure token.
        this.addAbility(new DealCombatDamageControlledTriggeredAbility(
                new CreateTokenEffect(new TreasureToken()), filter
        ));

        // {3}, Sacrifice two Treasures: Put two +1/+1 counters on each creature you control. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new AddCountersAllEffect(CounterType.P1P1.createInstance(2), StaticFilters.FILTER_CONTROLLED_CREATURE),
                new GenericManaCost(3)
        );
        ability.addCost(new SacrificeTargetCost(2, filterTreasure));
        this.addAbility(ability);
    }

    private OliviaOpulentOutlaw(final OliviaOpulentOutlaw card) {
        super(card);
    }

    @Override
    public OliviaOpulentOutlaw copy() {
        return new OliviaOpulentOutlaw(this);
    }
}
