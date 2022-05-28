package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SkipUntapOptionalAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInGraveyard;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author anonymous
 */
public final class CoffinQueen extends CardImpl {

    public CoffinQueen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // You may choose not to untap Coffin Queen during your untap step.
        this.addAbility(new SkipUntapOptionalAbility());

        // {2}{B}, {tap}: Put target creature card from a graveyard onto the battlefield under your control. When Coffin Queen becomes untapped or you lose control of Coffin Queen, exile that creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ReturnFromGraveyardToBattlefieldTargetEffect(), new ManaCostsImpl<>("{2}{B}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCardInGraveyard(new FilterCreatureCard("creature card from a graveyard")));
        ability.addEffect(new CoffinQueenCreateDelayedTriggerEffect());
        this.addAbility(ability);

    }

    private CoffinQueen(final CoffinQueen card) {
        super(card);
    }

    @Override
    public CoffinQueen copy() {
        return new CoffinQueen(this);
    }
}

class CoffinQueenCreateDelayedTriggerEffect extends OneShotEffect {

    public CoffinQueenCreateDelayedTriggerEffect() {
        super(Outcome.Detriment);
        this.staticText = "When {this} becomes untapped or you lose control of {this}, exile that creature.";
    }

    public CoffinQueenCreateDelayedTriggerEffect(final CoffinQueenCreateDelayedTriggerEffect effect) {
        super(effect);
    }

    @Override
    public CoffinQueenCreateDelayedTriggerEffect copy() {
        return new CoffinQueenCreateDelayedTriggerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent controlledCreature = game.getPermanent(source.getFirstTarget());
        if (controlledCreature != null) {
            DelayedTriggeredAbility delayedAbility = new CoffinQueenDelayedTriggeredAbility();
            delayedAbility.getEffects().get(0).setTargetPointer(new FixedTarget(controlledCreature, game));
            game.addDelayedTriggeredAbility(delayedAbility, source);
            return true;
        }
        return false;
    }
}

class CoffinQueenDelayedTriggeredAbility extends DelayedTriggeredAbility {

    CoffinQueenDelayedTriggeredAbility() {
        super(new ExileTargetEffect(), Duration.EndOfGame, true);
    }

    CoffinQueenDelayedTriggeredAbility(CoffinQueenDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.LOST_CONTROL
                || event.getType() == GameEvent.EventType.UNTAPPED
                || event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (EventType.LOST_CONTROL == event.getType()
                && event.getTargetId().equals(this.getSourceId())) {
            return true;
        }
        if (EventType.UNTAPPED == event.getType()
                && event.getTargetId() != null
                && event.getTargetId().equals(getSourceId())) {
            return true;
        }
        if (EventType.ZONE_CHANGE == event.getType()
                && event.getTargetId().equals(this.getSourceId())) {
            return true;
        }
        return false;
    }

    @Override
    public CoffinQueenDelayedTriggeredAbility copy() {
        return new CoffinQueenDelayedTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "When {this} becomes untapped or you lose control of {this}, exile that creature.";
    }
}
