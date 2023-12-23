package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AureliaTheLawAbove extends CardImpl {

    public AureliaTheLawAbove(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever a player attacks with three or more creatures, you draw a card.
        this.addAbility(new AureliaTheLawAboveTriggeredAbility(
                new DrawCardSourceControllerEffect(1, "you"), 3
        ));

        // Whenever a player attacks with five or more creatures, Aurelia, the Law Above deals 3 damage to each of your opponents and you gain 3 life.
        Ability ability = new AureliaTheLawAboveTriggeredAbility(
                new DamagePlayersEffect(3, TargetController.OPPONENT)
                        .setText("{this} deals 3 damage to each of your opponents"), 5
        );
        ability.addEffect(new GainLifeEffect(3).concatBy("and"));
        this.addAbility(ability);
    }

    private AureliaTheLawAbove(final AureliaTheLawAbove card) {
        super(card);
    }

    @Override
    public AureliaTheLawAbove copy() {
        return new AureliaTheLawAbove(this);
    }
}

class AureliaTheLawAboveTriggeredAbility extends TriggeredAbilityImpl {

    private final int amount;

    AureliaTheLawAboveTriggeredAbility(Effect effect, int amount) {
        super(Zone.BATTLEFIELD, effect);
        this.amount = amount;
        this.setTriggerPhrase("Whenever a player attacks with " + CardUtil.numberToText(amount) + " or more creatures, ");
    }

    private AureliaTheLawAboveTriggeredAbility(final AureliaTheLawAboveTriggeredAbility ability) {
        super(ability);
        this.amount = ability.amount;
    }

    @Override
    public AureliaTheLawAboveTriggeredAbility copy() {
        return new AureliaTheLawAboveTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return game.getCombat().getAttackers().size() >= amount;
    }
}
