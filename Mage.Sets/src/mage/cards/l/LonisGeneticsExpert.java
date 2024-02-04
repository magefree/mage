package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.OneOrMoreCountersAddedTriggeredAbility;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.abilities.keyword.EvolveAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class LonisGeneticsExpert extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("a Clue");
    static {
        filter.add(SubType.CLUE.getPredicate());
    }

    public LonisGeneticsExpert(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G/U}{G/U}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SNAKE);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DETECTIVE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Evolve
        this.addAbility(new EvolveAbility());

        // Whenever one or more +1/+1 counters are put on Lonis, investigate that many times.
        this.addAbility(new LonisGeneticsExpertTriggeredAbility());

        // Whenever you sacrifice a Clue, put a +1/+1 counter on another target creature you control.
        Ability ability = new SacrificePermanentTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()), filter);
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE_YOU_CONTROL));
        this.addAbility(ability);
    }

    private LonisGeneticsExpert(final LonisGeneticsExpert card) {
        super(card);
    }

    @Override
    public LonisGeneticsExpert copy() {
        return new LonisGeneticsExpert(this);
    }
}

class LonisGeneticsExpertTriggeredAbility extends OneOrMoreCountersAddedTriggeredAbility {

    LonisGeneticsExpertTriggeredAbility() {
        super(new InvestigateEffect(SavedDamageValue.MANY).setText("investigate that many times"));
    }

    private LonisGeneticsExpertTriggeredAbility(final LonisGeneticsExpertTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public LonisGeneticsExpertTriggeredAbility copy() {
        return new LonisGeneticsExpertTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (super.checkTrigger(event, game)) {
            this.getEffects().setValue("damage", event.getAmount());
            return true;
        }
        return false;
    }

}
