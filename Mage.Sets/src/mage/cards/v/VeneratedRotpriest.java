package mage.cards.v;

import java.util.UUID;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.FightTargetSourceEffect;
import mage.abilities.effects.common.counter.AddPoisonCounterTargetEffect;
import mage.constants.SubType;
import mage.abilities.keyword.ToxicAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.target.TargetPermanent;
import mage.target.common.TargetOpponent;

/**
 * @author TheElk801
 */
public final class VeneratedRotpriest extends CardImpl {

    public VeneratedRotpriest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Toxic 1
        this.addAbility(new ToxicAbility(1));

        // Whenever a creature you control becomes the target of a spell, target opponent gets a poison counter.
        this.addAbility(new VeneratedRotpriestTriggeredAbility());
    }

    private VeneratedRotpriest(final VeneratedRotpriest card) {
        super(card);
    }

    @Override
    public VeneratedRotpriest copy() {
        return new VeneratedRotpriest(this);
    }
}

class VeneratedRotpriestTriggeredAbility extends TriggeredAbilityImpl {

    VeneratedRotpriestTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddPoisonCounterTargetEffect(1));
        this.addTarget(new TargetOpponent());
    }

    private VeneratedRotpriestTriggeredAbility(final VeneratedRotpriestTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public VeneratedRotpriestTriggeredAbility copy() {
        return new VeneratedRotpriestTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TARGETED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        MageObject object = game.getObject(event.getSourceId());
        return permanent != null
                && object != null
                && isControlledBy(permanent.getControllerId())
                && permanent.isCreature(game)
                && object instanceof Spell;
    }

    @Override
    public String getRule() {
        return "Whenever a creature you control becomes the target of a spell, target opponent gets a poison counter.";
    }
}
