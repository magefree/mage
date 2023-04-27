package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.combat.GoadTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MaeveInsidiousSinger extends CardImpl {

    public MaeveInsidiousSinger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SIREN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // {2}{U}: Goad target creature. Whenever that creature attacks one of your opponents this turn, you draw a card.
        Ability ability = new SimpleActivatedAbility(new GoadTargetEffect(), new ManaCostsImpl<>("{2}{U}"));
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new MaeveInsidiousSingerTriggeredAbility()));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private MaeveInsidiousSinger(final MaeveInsidiousSinger card) {
        super(card);
    }

    @Override
    public MaeveInsidiousSinger copy() {
        return new MaeveInsidiousSinger(this);
    }
}

class MaeveInsidiousSingerTriggeredAbility extends DelayedTriggeredAbility {

    MaeveInsidiousSingerTriggeredAbility() {
        super(new DrawCardSourceControllerEffect(1), Duration.EndOfTurn, false, false);
    }

    private MaeveInsidiousSingerTriggeredAbility(final MaeveInsidiousSingerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public MaeveInsidiousSingerTriggeredAbility copy() {
        return new MaeveInsidiousSingerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return this
                .getEffects()
                .stream()
                .map(Effect::getTargetPointer)
                .map(targetPointer -> targetPointer.getFirst(game, this))
                .anyMatch(event.getSourceId()::equals)
                && game
                .getOpponents(getControllerId())
                .contains(event.getTargetId());
    }

    @Override
    public String getRule() {
        return "Whenever that creature attacks one of your opponents this turn, you draw a card.";
    }
}
