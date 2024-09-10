package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.card.ManaValueLessThanOrEqualToSourcePowerPredicate;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 *
 * @author skaspels
 */
public final class CarmenCruelSkymarcher extends CardImpl {

    private static final FilterCard filter = new FilterPermanentCard(
            "permanent card with mana value less than or equal to {this}'s power from your graveyard");
    static {
        filter.add(ManaValueLessThanOrEqualToSourcePowerPredicate.instance);
    }
    public CarmenCruelSkymarcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{B}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever a player sacrifices a permanent, put a +1/+1 counter on Carmen, Cruel Skymarcher and you gain 1 life.
        Ability sacTrigger = new SacrificePermanentTriggeredAbility(Zone.BATTLEFIELD,
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                StaticFilters.FILTER_PERMANENT, TargetController.ANY, SetTargetPointer.NONE, false);
        sacTrigger.addEffect(new GainLifeEffect(1).concatBy("and"));
        this.addAbility(sacTrigger);

        // Whenever Carmen attacks, return up to one target permanent card with mana value less than or equal to Carmen's power from your graveyard to the battlefield.
        Ability ability = new AttacksTriggeredAbility(new ReturnFromGraveyardToBattlefieldTargetEffect());
        ability.addTarget(new TargetCardInYourGraveyard(0,1,filter));
        this.addAbility(ability);
    }

    private CarmenCruelSkymarcher(final CarmenCruelSkymarcher card) {
        super(card);
    }

    @Override
    public CarmenCruelSkymarcher copy() {
        return new CarmenCruelSkymarcher(this);
    }
}
