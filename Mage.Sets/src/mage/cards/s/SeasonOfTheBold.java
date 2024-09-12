package mage.cards.s;

import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.Mode;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.TreasureToken;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author notgreat
 */
public final class SeasonOfTheBold extends CardImpl {

    public SeasonOfTheBold(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}{R}");


        // Choose up to five {P} worth of modes. You may choose the same mode more than once.
        this.getSpellAbility().getModes().setMaxPawPrints(5);
        this.getSpellAbility().getModes().setMinModes(0);
        this.getSpellAbility().getModes().setMaxModes(5);
        this.getSpellAbility().getModes().setMayChooseSameModeMoreThanOnce(true);

        // {P} -- Create a tapped Treasure token.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new TreasureToken(), 1, true));
        this.spellAbility.getModes().getMode().withPawPrintValue(1);

        // {P}{P} -- Exile the top two cards of your library. Until the end of your next turn, you may play them.
        Mode mode2 = new Mode(new ExileTopXMayPlayUntilEffect(2, Duration.UntilEndOfYourNextTurn));
        this.getSpellAbility().addMode(mode2.withPawPrintValue(2));

        // {P}{P}{P} -- Until the end of your next turn, whenever you cast a spell, Season of the Bold deals 2 damage to up to one target creature.
        Mode mode3 = new Mode(new CreateDelayedTriggeredAbilityEffect(new SeasonOfTheBoldDelayedTriggeredAbility()));
        this.getSpellAbility().addMode(mode3.withPawPrintValue(3));
    }

    private SeasonOfTheBold(final SeasonOfTheBold card) {
        super(card);
    }

    @Override
    public SeasonOfTheBold copy() {
        return new SeasonOfTheBold(this);
    }
}

// Based on ShowdownOfTheSkaldsDelayedTriggeredAbility
class SeasonOfTheBoldDelayedTriggeredAbility extends DelayedTriggeredAbility {

    SeasonOfTheBoldDelayedTriggeredAbility() {
        super(new DamageTargetEffect(2), Duration.UntilEndOfYourNextTurn, false, false);
        this.addTarget(new TargetCreaturePermanent(0, 1));
    }

    private SeasonOfTheBoldDelayedTriggeredAbility(final SeasonOfTheBoldDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SeasonOfTheBoldDelayedTriggeredAbility copy() {
        return new SeasonOfTheBoldDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(this.getControllerId());
    }

    @Override
    public String getRule() {
        return "Until the end of your next turn, whenever you cast a spell, {this} deals 2 damage to up to one target creature.";
    }
}
