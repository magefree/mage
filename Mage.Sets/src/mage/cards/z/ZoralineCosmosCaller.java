package mage.cards.z;

import mage.MageInt;
import mage.abilities.common.AttacksCreatureYouControlTriggeredAbility;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.CompositeCost;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldWithCounterTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class ZoralineCosmosCaller extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.BAT, "Bat you control");


    private static final FilterPermanentCard filterCard = new FilterPermanentCard("nonland permanent card with mana value 3 or less from your graveyard");

    static {
        filterCard.add(Predicates.not(CardType.LAND.getPredicate()));
        filterCard.add(new ManaValuePredicate(ComparisonType.OR_LESS, 3));
    }

    public ZoralineCosmosCaller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BAT);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever a Bat you control attacks, you gain 1 life.
        this.addAbility(new AttacksCreatureYouControlTriggeredAbility(
                new GainLifeEffect(1), false, filter
        ));

        // Whenever Zoraline enters or attacks, you may pay {W}{B} and 2 life. When you do, return target nonland permanent card with mana value 3 or less from your graveyard to the battlefield with a finality counter on it.
        ReflexiveTriggeredAbility reflexive = new ReflexiveTriggeredAbility(
                new ReturnFromGraveyardToBattlefieldWithCounterTargetEffect(CounterType.FINALITY.createInstance()), false
        );
        reflexive.addTarget(new TargetCardInYourGraveyard(filterCard));
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(
                new DoWhenCostPaid(
                        reflexive,
                        new CompositeCost(new ManaCostsImpl<>("{W}{B}"), new PayLifeCost(2), "{W}{B} and 2 life"),
                        "pay {W}{B} and 2 life?"
                )
        ));
    }

    private ZoralineCosmosCaller(final ZoralineCosmosCaller card) {
        super(card);
    }

    @Override
    public ZoralineCosmosCaller copy() {
        return new ZoralineCosmosCaller(this);
    }
}
