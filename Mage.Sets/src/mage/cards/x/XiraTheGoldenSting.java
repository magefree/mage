package mage.cards.x;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.delayed.WhenTargetDiesDelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.XiraBlackInsectToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class XiraTheGoldenSting extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("another target creature without an egg counter on it");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(Predicates.not(CounterType.EGG.getPredicate()));
    }

    public XiraTheGoldenSting(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{R}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.INSECT, SubType.ASSASSIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Xira, the Golden Sting attacks, put an egg counter on another target creature without an egg counter on it.
        // When that creature dies, if it has an egg counter on it, draw a card and create a 1/1 black Insect creature token with flying.
        Ability ability = new AttacksTriggeredAbility(new AddCountersTargetEffect(CounterType.EGG.createInstance()));
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new XiraTheGoldenStingTriggeredAbility()));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private XiraTheGoldenSting(final XiraTheGoldenSting card) {
        super(card);
    }

    @Override
    public XiraTheGoldenSting copy() {
        return new XiraTheGoldenSting(this);
    }
}

class XiraTheGoldenStingTriggeredAbility extends WhenTargetDiesDelayedTriggeredAbility {

    XiraTheGoldenStingTriggeredAbility() {
        super(new DrawCardSourceControllerEffect(1), Duration.Custom, SetTargetPointer.NONE);
        this.addEffect(new CreateTokenEffect(new XiraBlackInsectToken()).concatBy("and"));
        setTriggerPhrase("When that creature dies, if it has an egg counter on it, ");
    }

    private XiraTheGoldenStingTriggeredAbility(final XiraTheGoldenStingTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public XiraTheGoldenStingTriggeredAbility copy() {
        return new XiraTheGoldenStingTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = ((ZoneChangeEvent) event).getTarget();
        return permanent != null && permanent.getCounters(game).containsKey(CounterType.EGG) && super.checkTrigger(event, game);
    }

    @Override
    public boolean isInactive(Game game) {
        int zccdiff = game.getState().getZoneChangeCounter(mor.getSourceId()) - mor.getZoneChangeCounter();
        return zccdiff > 1 || zccdiff > 0 && game.getState().getZone(mor.getSourceId()) != Zone.GRAVEYARD;
    }
}
