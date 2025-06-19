package mage.cards.a;

import mage.MageInt;
import mage.abilities.BatchTriggeredAbility;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.LoseHalfLifeTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.events.DamagedBatchForOnePlayerEvent;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.token.OphiomancerSnakeToken;
import mage.target.targetpointer.FixedTarget;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ApheliaViperWhisperer extends CardImpl {

    public ApheliaViperWhisperer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GORGON);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Whenever Aphelia attacks, you may pay {1}{B/G}. If you do, create a 1/1 black Snake creature token with deathtouch.
        this.addAbility(new AttacksTriggeredAbility(new DoIfCostPaid(
                new CreateTokenEffect(new OphiomancerSnakeToken()), new ManaCostsImpl<>("{1}{B/G}")
        )));

        // {4}{B}: Until end of turn, whenever one or more Gorgons and/or Snakes you control deal combat damage to a player, that player loses half their life, rounded up.
        this.addAbility(new SimpleActivatedAbility(
                new CreateDelayedTriggeredAbilityEffect(new ApheliaViperWhispererTriggeredAbility()), new ManaCostsImpl<>("{4}{B}")
        ));
    }

    private ApheliaViperWhisperer(final ApheliaViperWhisperer card) {
        super(card);
    }

    @Override
    public ApheliaViperWhisperer copy() {
        return new ApheliaViperWhisperer(this);
    }
}

class ApheliaViperWhispererTriggeredAbility extends DelayedTriggeredAbility implements BatchTriggeredAbility<DamagedPlayerEvent> {

    ApheliaViperWhispererTriggeredAbility() {
        super(new LoseHalfLifeTargetEffect(), Duration.EndOfTurn, false, false);
        setTriggerPhrase("Until end of turn, whenever one or more Gorgons and/or Snakes you control deal combat damage to a player, ");
    }

    private ApheliaViperWhispererTriggeredAbility(final ApheliaViperWhispererTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ApheliaViperWhispererTriggeredAbility copy() {
        return new ApheliaViperWhispererTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_BATCH_FOR_ONE_PLAYER;
    }

    @Override
    public boolean checkEvent(DamagedPlayerEvent event, Game game) {
        return event.isCombatDamage()
                && Optional
                .ofNullable(event)
                .map(GameEvent::getSourceId)
                .map(game::getPermanentOrLKIBattlefield)
                .filter(permanent -> permanent.hasSubtype(SubType.GORGON, game)
                        || permanent.hasSubtype(SubType.SNAKE, game))
                .map(Controllable::getControllerId)
                .filter(this::isControlledBy)
                .isPresent();
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (this.getFilteredEvents((DamagedBatchForOnePlayerEvent) event, game).isEmpty()) {
            return false;
        }
        this.getAllEffects().setTargetPointer(new FixedTarget(event.getTargetId()));
        return true;
    }
}
