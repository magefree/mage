package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.ExhaustAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetCardInYourGraveyard;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class LilianaTheRepentant extends CardImpl {

    private static final FilterPermanent filter
        = new FilterCreatureOrPlaneswalkerPermanent("another creature or planeswalker you control");
    private static final FilterCard filterCard
        = new FilterCard("creature or planeswalker card from your graveyard");


    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TargetController.YOU.getControllerPredicate());
        filterCard.add(Predicates.or(
            CardType.CREATURE.getPredicate(),
            CardType.PLANESWALKER.getPredicate()
        ));
    }

    public LilianaTheRepentant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever another creature or planeswalker you control enters, mill two cards.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
            new MillCardsControllerEffect(2), filter
        ));

        // Exhaust -- {5}{B}: Return target creature or planeswalker card from your graveyard to the battlefield. Put a +1/+1 counter on Liliana. Activate only as a sorcery.
        Ability ability = new ExhaustAbility(
            new ReturnFromGraveyardToBattlefieldTargetEffect(),
            new ManaCostsImpl<>("{5}{B}"),
            true,
            true
        );
        ability.addEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance()));
        ability.addTarget(new TargetCardInYourGraveyard(filterCard));
        this.addAbility(ability);
    }

    private LilianaTheRepentant(final LilianaTheRepentant card) {
        super(card);
    }

    @Override
    public LilianaTheRepentant copy() {
        return new LilianaTheRepentant(this);
    }
}
