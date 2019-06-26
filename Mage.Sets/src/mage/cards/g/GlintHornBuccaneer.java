package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.condition.common.SourceAttackingCondition;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GlintHornBuccaneer extends CardImpl {

    public GlintHornBuccaneer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{R}");

        this.subtype.add(SubType.MINOTAUR);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever you discard a card, Glint-Horn Buccaneer deals 1 damage to each opponent.
        this.addAbility(new GlintHornBuccaneerTriggeredAbility());

        // {1}{R}, Discard a card: Draw a card. Activate this ability only if Glint-Horn Buccaneer is attacking.
        Ability ability = new ConditionalActivatedAbility(
                Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1),
                new ManaCostsImpl("{1}{R}"), SourceAttackingCondition.instance
        );
        ability.addCost(new DiscardCardCost());
        this.addAbility(ability);
    }

    private GlintHornBuccaneer(final GlintHornBuccaneer card) {
        super(card);
    }

    @Override
    public GlintHornBuccaneer copy() {
        return new GlintHornBuccaneer(this);
    }
}

class GlintHornBuccaneerTriggeredAbility extends TriggeredAbilityImpl {

    GlintHornBuccaneerTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamagePlayersEffect(1, TargetController.OPPONENT), false);
    }

    private GlintHornBuccaneerTriggeredAbility(final GlintHornBuccaneerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public GlintHornBuccaneerTriggeredAbility copy() {
        return new GlintHornBuccaneerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DISCARDED_CARD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return isControlledBy(event.getPlayerId());

    }

    @Override
    public String getRule() {
        return "Whenever you discard a card, " + super.getRule();
    }
}
