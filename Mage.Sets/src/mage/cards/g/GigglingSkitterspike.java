package mage.cards.g;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerValue;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.keyword.MonstrosityAbility;
import mage.constants.SubType;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.StackObject;
import mage.util.CardUtil;

/**
 * @author Cguy7777
 */
public final class GigglingSkitterspike extends CardImpl {

    public GigglingSkitterspike(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");

        this.subtype.add(SubType.TOY);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());

        // Whenever Giggling Skitterspike attacks, blocks, or becomes the target of a spell,
        // it deals damage equal to its power to each opponent.
        this.addAbility(new GigglingSkitterspikeTriggeredAbility());

        // {5}: Monstrosity 5.
        this.addAbility(new MonstrosityAbility("{5}", 5));

    }

    private GigglingSkitterspike(final GigglingSkitterspike card) {
        super(card);
    }

    @Override
    public GigglingSkitterspike copy() {
        return new GigglingSkitterspike(this);
    }
}

class GigglingSkitterspikeTriggeredAbility extends TriggeredAbilityImpl {

    GigglingSkitterspikeTriggeredAbility() {
        super(Zone.BATTLEFIELD,
                new DamagePlayersEffect(SourcePermanentPowerValue.NOT_NEGATIVE, TargetController.OPPONENT)
                        .setText("it deals damage equal to its power to each opponent"));
        setTriggerPhrase("Whenever {this} attacks, blocks, or becomes the target of a spell, ");
    }

    private GigglingSkitterspikeTriggeredAbility(final GigglingSkitterspikeTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED
                || event.getType() == GameEvent.EventType.CREATURE_BLOCKS
                || event.getType() == GameEvent.EventType.TARGETED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ATTACKER_DECLARED) {
            return getSourceId().equals(event.getSourceId());
        } else if (event.getType() == GameEvent.EventType.CREATURE_BLOCKS) {
            return getSourceId().equals(event.getTargetId());
        }

        if (!event.getTargetId().equals(getSourceId())) {
            return false;
        }
        StackObject targetingObject = CardUtil.getTargetingStackObject(event, game);
        if (targetingObject == null
                || !StaticFilters.FILTER_SPELL.match(targetingObject, getControllerId(), this, game)) {
            return false;
        }
        if (CardUtil.checkTargetedEventAlreadyUsed(this.getId().toString(), targetingObject, event, game)) {
            return false;
        }

        return true;
    }

    @Override
    public GigglingSkitterspikeTriggeredAbility copy() {
        return new GigglingSkitterspikeTriggeredAbility(this);
    }
}
