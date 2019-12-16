package mage.cards.k;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.continuous.LoseCreatureTypeSourceEffect;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;
import mage.watchers.common.CardsAmountDrawnThisTurnWatcher;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class KeranosGodOfStorms extends CardImpl {

    private static final DynamicValue xValue = new DevotionCount(ColoredManaSymbol.U, ColoredManaSymbol.R);

    public KeranosGodOfStorms(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{3}{U}{R}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOD);

        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());

        // As long as your devotion to blue and red is less than seven, Keranos isn't a creature.
        Effect effect = new LoseCreatureTypeSourceEffect(xValue, 7);
        effect.setText("As long as your devotion to blue and red is less than seven, Keranos isn't a creature");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect).addHint(new ValueHint("Devotion to blue and red", xValue)));

        // Reveal the first card you draw on each of your turns. 
        // Whenever you reveal a land card this way, draw a card. 
        // Whenever you reveal a nonland card this way, Keranos deals 3 damage to any target.
        this.addAbility(new KeranosGodOfStormsTriggeredAbility(), new CardsAmountDrawnThisTurnWatcher());


    }

    public KeranosGodOfStorms(final KeranosGodOfStorms card) {
        super(card);
    }

    @Override
    public KeranosGodOfStorms copy() {
        return new KeranosGodOfStorms(this);
    }
}

class KeranosGodOfStormsTriggeredAbility extends TriggeredAbilityImpl {

    KeranosGodOfStormsTriggeredAbility() {
        super(Zone.BATTLEFIELD, new InfoEffect(""), false);
    }

    KeranosGodOfStormsTriggeredAbility(final KeranosGodOfStormsTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public KeranosGodOfStormsTriggeredAbility copy() {
        return new KeranosGodOfStormsTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DREW_CARD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(this.getControllerId())) {
            if (game.isActivePlayer(this.getControllerId())) {
                CardsAmountDrawnThisTurnWatcher watcher =
                        game.getState().getWatcher(CardsAmountDrawnThisTurnWatcher.class);
                if (watcher != null && watcher.getAmountCardsDrawn(event.getPlayerId()) != 1) {
                    return false;
                }
                Card card = game.getCard(event.getTargetId());
                Player controller = game.getPlayer(this.getControllerId());
                Permanent sourcePermanent = (Permanent) getSourceObject(game);
                if (card != null && controller != null && sourcePermanent != null) {
                    controller.revealCards(sourcePermanent.getIdName(), new CardsImpl(card), game);
                    this.getTargets().clear();
                    this.getEffects().clear();
                    if (card.isLand()) {
                        this.addEffect(new DrawCardSourceControllerEffect(1));
                    } else {
                        this.addEffect(new DamageTargetEffect(3));
                        this.addTarget(new TargetAnyTarget());
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Reveal the first card you draw on each of your turns. Whenever you reveal a land card this way, draw a card. " +
                "Whenever you reveal a nonland card this way, Keranos deals 3 damage to any target.";
    }
}
