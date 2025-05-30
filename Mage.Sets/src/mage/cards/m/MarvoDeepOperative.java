package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ClashTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.cost.CastFromHandForFreeEffect;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author Grath
 */
public final class MarvoDeepOperative extends CardImpl {

    private static final FilterCard filter = new FilterCard();

    static {
        filter.add(new ManaValuePredicate(ComparisonType.OR_LESS, 8));
    }

    public MarvoDeepOperative(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{B}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.OCTOPUS);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(8);

        // Whenever Marvo, Deep Operative attacks, clash with defending player.
        this.addAbility(new AttacksTriggeredAbility(new ClashTargetEffect().setText("clash with defending player"), false, null, SetTargetPointer.PLAYER));

        // Whenever you win a clash, draw a card. Then you may cast a spell from your hand with mana value 8 or less
        // without paying its mana cost.
        Ability ability = new MarvoDeepOperativeTriggeredAbility(new DrawCardSourceControllerEffect(1, true));
        ability.addEffect(new CastFromHandForFreeEffect(filter));
        this.addAbility(ability);

    }

    private MarvoDeepOperative(final MarvoDeepOperative card) {
        super(card);
    }

    @Override
    public MarvoDeepOperative copy() {
        return new MarvoDeepOperative(this);
    }
}

class MarvoDeepOperativeTriggeredAbility extends TriggeredAbilityImpl {

    MarvoDeepOperativeTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect);
    }

    private MarvoDeepOperativeTriggeredAbility(final MarvoDeepOperativeTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public MarvoDeepOperativeTriggeredAbility copy() {
        return new MarvoDeepOperativeTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CLASHED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return isControlledBy(event.getPlayerId()) && event.getFlag();
    }

    @Override
    public String getRule() {
        return "Whenever you win a clash, draw a card. Then you may cast a spell from your hand with mana value 8 or " +
                "less without paying its mana cost.";
    }
}