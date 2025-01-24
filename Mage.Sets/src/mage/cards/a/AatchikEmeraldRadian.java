package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.permanent.token.InsectToken;
import mage.constants.SubType;

/**
 *
 * @author jackd149
 */
public final class AatchikEmeraldRadian extends CardImpl {
    private static final FilterCard filter1 = new FilterCard("artifact and/or creature card");
    private static final FilterControlledPermanent filter2 = new FilterControlledPermanent(SubType.INSECT, "another Insect you control");
    private static final CardsInControllerGraveyardCount count = new CardsInControllerGraveyardCount(filter1);
    
    static {
        filter1.add(Predicates.or(CardType.ARTIFACT.getPredicate(), CardType.CREATURE.getPredicate()));
        filter2.add(AnotherPredicate.instance);
    }

    public AatchikEmeraldRadian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{B}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.INSECT);
        this.subtype.add(SubType.DRUID);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        
        // When Aatchik, Emerald Radian enters, create a 1/1 green Insect creature token for each actifact and/or creature card in your graveyard.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new InsectToken(), count)));
        
        // Whenever another insect you control dies, put a +1/+1 counter on Aatchik. Each opponent loses 1 life.
        DiesCreatureTriggeredAbility otherInsectDiesAbility = new DiesCreatureTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false, filter2);
        otherInsectDiesAbility.addEffect(new LoseLifeOpponentsEffect(1));
        this.addAbility(otherInsectDiesAbility);
    }

    private AatchikEmeraldRadian(final AatchikEmeraldRadian card) {
        super(card);
    }

    @Override
    public AatchikEmeraldRadian copy() {
        return new AatchikEmeraldRadian(this);
    }

}