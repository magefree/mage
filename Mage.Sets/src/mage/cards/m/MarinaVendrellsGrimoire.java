package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.GainLifeControllerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CastFromEverywhereSourceCondition;
import mage.abilities.condition.common.HellbentCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.dynamicvalue.common.SavedGainedLifeValue;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseGameSourceControllerEffect;
import mage.abilities.effects.common.continuous.DontLoseByZeroOrLessLifeEffect;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MarinaVendrellsGrimoire extends CardImpl {

    public MarinaVendrellsGrimoire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}{U}");

        this.supertype.add(SuperType.LEGENDARY);

        // When Marina Vendrell's Grimoire enters, if you cast it, draw five cards.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(5)),
                CastFromEverywhereSourceCondition.instance, "When {this} enters, if you cast it, draw five cards."
        ));

        // You have no maximum hand size and don't lose the game for having 0 or less life.
        Ability ability = new SimpleStaticAbility(new MaximumHandSizeControllerEffect(
                Integer.MAX_VALUE, Duration.WhileOnBattlefield,
                MaximumHandSizeControllerEffect.HandSizeModification.SET
        ));
        ability.addEffect(new DontLoseByZeroOrLessLifeEffect(Duration.WhileOnBattlefield)
                .setText("and don't lose the game for having 0 or less life"));
        this.addAbility(ability);

        // Whenever you gain life, draw that many cards.
        this.addAbility(new GainLifeControllerTriggeredAbility(
                new DrawCardSourceControllerEffect(SavedGainedLifeValue.MANY)
        ));

        // Whenever you lose life, discard that many cards. Then if you have no cards in hand, you lose the game.
        this.addAbility(new MarinaVendrellsGrimoireTriggeredAbility());
    }

    private MarinaVendrellsGrimoire(final MarinaVendrellsGrimoire card) {
        super(card);
    }

    @Override
    public MarinaVendrellsGrimoire copy() {
        return new MarinaVendrellsGrimoire(this);
    }
}

class MarinaVendrellsGrimoireTriggeredAbility extends TriggeredAbilityImpl {

    MarinaVendrellsGrimoireTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DiscardControllerEffect(SavedDamageValue.MANY));
        this.addEffect(new ConditionalOneShotEffect(
                new LoseGameSourceControllerEffect(), HellbentCondition.instance,
                "Then if you have no cards in hand, you lose the game"
        ));
        this.setTriggerPhrase("Whenever you lose life, ");
    }

    private MarinaVendrellsGrimoireTriggeredAbility(final MarinaVendrellsGrimoireTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public MarinaVendrellsGrimoireTriggeredAbility copy() {
        return new MarinaVendrellsGrimoireTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.LOST_LIFE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!isControlledBy(event.getPlayerId())) {
            return false;
        }
        this.getEffects().setValue("damage", event.getAmount());
        return true;
    }
}
