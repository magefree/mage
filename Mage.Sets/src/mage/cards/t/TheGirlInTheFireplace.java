package mage.cards.t;

import java.util.UUID;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.counter.TimeTravelEffect;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SagaChapter;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TheGirlInTheFireplaceHorseToken;
import mage.game.permanent.token.TheGirlInTheFireplaceHumanNobleToken;
import mage.filter.common.FilterControlledCreaturePermanent;

/**
 *
 * @author padfoot 
 */
public final class TheGirlInTheFireplace extends CardImpl {


    public TheGirlInTheFireplace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");
        
        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

	// I -- Create a 1/1 white Human Noble creature token with vanishing 3 and "Prevent all damage that would be dealt to this creature."
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, new CreateTokenEffect(new TheGirlInTheFireplaceHumanNobleToken()));

	// II -- Create a 2/2 white Horse creature token with "Doctors you control have horsemanship."
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_II, new CreateTokenEffect(new TheGirlInTheFireplaceHorseToken())
				.withAdditionalRules(" <i>(They can't be blocked except by creatures with horsemanship.)</i>"));
	
	// III -- Whenever a creature you control deals combat damage to a player this turn, time travel.
        sagaAbility.addChapterEffect(
		this, SagaChapter.CHAPTER_III,
		new CreateDelayedTriggeredAbilityEffect(
			new TheGirlInTheFireplaceTriggeredAbility()
		)
	); 

    this.addAbility(sagaAbility);

    }

    private TheGirlInTheFireplace(final TheGirlInTheFireplace card) {
        super(card);
    }

    @Override
    public TheGirlInTheFireplace copy() {
        return new TheGirlInTheFireplace(this);
    }
}

class TheGirlInTheFireplaceTriggeredAbility extends DelayedTriggeredAbility {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();
    
    public TheGirlInTheFireplaceTriggeredAbility() {
        super(new TimeTravelEffect(), Duration.EndOfTurn, false);
    }

    private TheGirlInTheFireplaceTriggeredAbility(TheGirlInTheFireplaceTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TheGirlInTheFireplaceTriggeredAbility copy() {
        return new TheGirlInTheFireplaceTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!((DamagedPlayerEvent) event).isCombatDamage()) {
            return false;
        }
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getSourceId());
        if (!filter.match(permanent, getControllerId(), this, game)) {
            return false;
        }
        return true; 
    }

    @Override
    public String getRule() {
        return "Whenever a creature you control deals combat damage to a player this turn, time travel.";
    }
}
