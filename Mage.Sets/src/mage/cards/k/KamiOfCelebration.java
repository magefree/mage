package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.AttacksCreatureYouControlTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEndOfTurnEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.ModifiedPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KamiOfCelebration extends CardImpl {

    private static final FilterControlledCreaturePermanent filter
            = new FilterControlledCreaturePermanent("a modified creature you control");

    static {
        filter.add(ModifiedPredicate.instance);
    }

    public KamiOfCelebration(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever a modified creature you control attacks, exile the top card of your library. You may play that card this turn.
        this.addAbility(new AttacksCreatureYouControlTriggeredAbility(
                new ExileTopXMayPlayUntilEndOfTurnEffect(1), false, filter
        ));

        // Whenever you cast a spell from exile, put a +1/+1 counter on target creature you control.
        this.addAbility(new KamiOfCelebrationAbility());
    }

    private KamiOfCelebration(final KamiOfCelebration card) {
        super(card);
    }

    @Override
    public KamiOfCelebration copy() {
        return new KamiOfCelebration(this);
    }
}

class KamiOfCelebrationAbility extends SpellCastControllerTriggeredAbility {

    KamiOfCelebrationAbility() {
        super(new AddCountersTargetEffect(CounterType.P1P1.createInstance()), false);
        this.addTarget(new TargetControlledCreaturePermanent());
    }

    private KamiOfCelebrationAbility(final KamiOfCelebrationAbility ability) {
        super(ability);
    }

    @Override
    public KamiOfCelebrationAbility copy() {
        return new KamiOfCelebrationAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getZone() == Zone.EXILED && super.checkTrigger(event, game);
    }

    @Override
    public String getRule() {
        return "Whenever you cast a spell from exile, put a +1/+1 counter on target creature you control.";
    }
}
