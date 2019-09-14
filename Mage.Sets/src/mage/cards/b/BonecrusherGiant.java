package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesTargetTriggeredAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BonecrusherGiant extends AdventureCard {

    public BonecrusherGiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.INSTANT}, "{2}{R}", "Stomp", "{1}{R}");

        this.subtype.add(SubType.GIANT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Whenever Bonecrusher Giant becomes the target of a spell, Bonecrusher Giant deals 2 damage to that spell's controller.
        this.addAbility(new BecomesTargetTriggeredAbility(new DamageTargetEffect(
                2, true, "that's spell's controller", "{this}"
        ), StaticFilters.FILTER_SPELL_A, SetTargetPointer.PLAYER));

        // Stomp
        // Damage can’t be prevented this turn. Stomp deals 2 damage to any target.
        this.getAdventureSpellAbility().addEffect(new StompEffect());
        this.getAdventureSpellAbility().addEffect(new DamageTargetEffect(2));
        this.getAdventureSpellAbility().addTarget(new TargetAnyTarget());
    }

    private BonecrusherGiant(final BonecrusherGiant card) {
        super(card);
    }

    @Override
    public BonecrusherGiant copy() {
        return new BonecrusherGiant(this);
    }
}

class StompEffect extends ReplacementEffectImpl {

    StompEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit);
        staticText = "Damage can't be prevented this turn.";
    }

    private StompEffect(final StompEffect effect) {
        super(effect);
    }

    @Override
    public StompEffect copy() {
        return new StompEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.PREVENT_DAMAGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return true;
    }
}
