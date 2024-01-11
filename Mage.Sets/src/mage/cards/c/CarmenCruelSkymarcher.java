package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.Card;
import mage.constants.*;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author skaspels
 */
public final class CarmenCruelSkymarcher extends CardImpl {

    private static final FilterCard filter = new FilterPermanentCard(
            "permanent card with mana value less than or equal to {this}'s power from your graveyard");
    static {
        filter.add(CarmenPredicate.instance);
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
        this.addAbility(new CarmenTriggeredAbility());

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

class CarmenTriggeredAbility extends TriggeredAbilityImpl {

    CarmenTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance()));
        this.addEffect(new GainLifeEffect(1));
    }

    private CarmenTriggeredAbility(final CarmenTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SACRIFICED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return true;
    }

    @Override
    public CarmenTriggeredAbility copy() {
        return new CarmenTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever a player sacrifices a permanent, put a +1/+1 counter on {this} and you gain 1 life.";
    }
}
enum CarmenPredicate implements ObjectSourcePlayerPredicate<Card> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
        Permanent sourcePermanent = input.getSource().getSourcePermanentOrLKI(game);
        return sourcePermanent != null
                && input.getObject().getManaValue() <= sourcePermanent.getPower().getValue();
    }
}
