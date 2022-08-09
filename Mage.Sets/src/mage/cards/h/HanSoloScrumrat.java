package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.*;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.PartnerWithAbility;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author NinthWorld
 */
public final class HanSoloScrumrat extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("another target creature you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public HanSoloScrumrat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Partner with Chewbacca, the Beast
        this.addAbility(new PartnerWithAbility("Chewbacca, the Beast"));

        // R: Han Solo, Scrumrat gains first strike until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilitySourceEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn), new ManaCostsImpl<>("{R}")));

        // Whenever Han Solo, Scrumrat deals damage during your turn, put a +1/+1 counter on another target creature you control.
        Ability ability = new HanSoloScrumratTriggeredAbility();
        ability.addTarget(new TargetControlledCreaturePermanent(filter));
        this.addAbility(ability);
    }

    private HanSoloScrumrat(final HanSoloScrumrat card) {
        super(card);
    }

    @Override
    public HanSoloScrumrat copy() {
        return new HanSoloScrumrat(this);
    }
}

class HanSoloScrumratTriggeredAbility extends TriggeredAbilityImpl {

    public HanSoloScrumratTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.P1P1.createInstance()), false);
        setTriggerPhrase("Whenever {this} creature deals damage during your turn, ");
    }

    public HanSoloScrumratTriggeredAbility(final HanSoloScrumratTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public HanSoloScrumratTriggeredAbility copy() {
        return new HanSoloScrumratTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PERMANENT
                || event.getType() ==  GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent source = game.getPermanent(this.getSourceId());
        return source != null
                && game.isActivePlayer(source.getControllerId())
                && event.getSourceId().equals(this.getSourceId());
    }
}