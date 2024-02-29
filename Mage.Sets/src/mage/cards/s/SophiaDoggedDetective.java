package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.FoodToken;
import mage.game.permanent.token.TinyToken;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class SophiaDoggedDetective extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent(SubType.DOG, "Dog you control");
    private static final FilterArtifactPermanent filter2 = new FilterArtifactPermanent("artifact token");

    static {
        filter2.add(TokenPredicate.TRUE);
    }

    public SophiaDoggedDetective(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{W}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN, SubType.DETECTIVE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // When Sophia, Dogged Detective enters the battlefield, create Tiny, a legendary 2/2 green Dog Detective creature token with trample.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new TinyToken())));

        // {1}, Sacrifice an artifact token: Put a +1/+1 counter on each Dog you control.
        Ability ability = new SimpleActivatedAbility(new AddCountersAllEffect(CounterType.P1P1.createInstance(), filter), new GenericManaCost(1));
        ability.addCost(new SacrificeTargetCost(filter2));
        this.addAbility(ability);

        // Whenever a Dog you control deals combat damage to a player, create a Food token, then investigate.
        ability = new DealsDamageToAPlayerAllTriggeredAbility(new CreateTokenEffect(new FoodToken()), filter, false, SetTargetPointer.NONE, true);
        ability.addEffect(new InvestigateEffect(false).concatBy(", then"));
        this.addAbility(ability);
    }

    private SophiaDoggedDetective(final SophiaDoggedDetective card) {
        super(card);
    }

    @Override
    public SophiaDoggedDetective copy() {
        return new SophiaDoggedDetective(this);
    }
}
