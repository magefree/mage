
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.stack.Spell;

/**
 *
 * @author LevelX2
 */
public final class Witchstalker extends CardImpl {

    public Witchstalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}{G}");
        this.subtype.add(SubType.WOLF);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Hexproof
        this.addAbility(HexproofAbility.getInstance());
        // Whenever an opponent casts a blue or black spell during your turn, put a +1/+1 counter on Witchstalker.
        this.addAbility(new WitchstalkerTriggeredAbility());

    }

    private Witchstalker(final Witchstalker card) {
        super(card);
    }

    @Override
    public Witchstalker copy() {
        return new Witchstalker(this);
    }
}

class WitchstalkerTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterCard filter = new FilterCard("blue or black spell");
    static {
        filter.add(Predicates.or(
                new ColorPredicate(ObjectColor.BLUE),
                new ColorPredicate(ObjectColor.BLACK)));
    }
    public WitchstalkerTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false);
    }

    private WitchstalkerTriggeredAbility(final WitchstalkerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Spell spell = game.getStack().getSpell(event.getTargetId());
        return spell != null
                && filter.match(spell,game)
                && game.getOpponents(this.getControllerId()).contains(spell.getControllerId())
                && game.isActivePlayer(this.getControllerId());
    }

    @Override
    public String getRule() {
        return "Whenever an opponent casts a blue or black spell during your turn, put a +1/+1 counter on {this}.";
    }

    @Override
    public WitchstalkerTriggeredAbility copy() {
        return new WitchstalkerTriggeredAbility(this);
    }
}
