package mage.cards.s;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;

import java.util.UUID;

/**
 * @author freaisdead
 */
public final class ShanidSleepersScourge extends CardImpl {
    private static final FilterCreaturePermanent otherLegendaryCreaturesFilter = new FilterCreaturePermanent();

    static {
        otherLegendaryCreaturesFilter.add(SuperType.LEGENDARY.getPredicate());
    }

    public ShanidSleepersScourge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{W}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Menace
        this.addAbility(new MenaceAbility());

        // Other legendary creatures you control have menace.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new MenaceAbility(false),
                Duration.WhileOnBattlefield,
                otherLegendaryCreaturesFilter,
                true)));
        // Whenever you play a legendary land or cast a legendary spell, you draw a card and you lose 1 life.
        this.addAbility(new DrawAndLoseLife(1, 1));
    }

    private ShanidSleepersScourge(final ShanidSleepersScourge card) {
        super(card);
    }

    @Override
    public ShanidSleepersScourge copy() {
        return new ShanidSleepersScourge(this);
    }
}


class DrawAndLoseLife extends TriggeredAbilityImpl {

    public DrawAndLoseLife(int drawAmount, int loseLifeAmount) {
        super(Zone.BATTLEFIELD, new LoseLifeSourceControllerEffect(loseLifeAmount), false);
        this.addEffect(new DrawCardSourceControllerEffect(drawAmount));
    }

    public DrawAndLoseLife(DrawAndLoseLife ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.LAND_PLAYED || event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(this.getControllerId())) {
            return false;
        } else if (event.getType() == GameEvent.EventType.LAND_PLAYED) {
            Permanent land = game.getPermanent(event.getTargetId());
            return land != null && land.getControllerId().equals(this.getControllerId()) && land.getSuperType().contains(SuperType.LEGENDARY);
        } else if (event.getType() == GameEvent.EventType.SPELL_CAST) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            return spell != null && spell.getControllerId().equals(this.getControllerId()) && spell.getSuperType().contains(SuperType.LEGENDARY);
        } else {
            return false;
        }
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        Player player = game.getPlayer(this.getControllerId());
        if (player != null) {
            return player.getLandsPlayed() != 1;
        }
        return false;
    }

    @Override
    public DrawAndLoseLife copy() {
        return new DrawAndLoseLife(this);
    }

    @Override
    public String getRule() {
        return "Whenever you play a legendary land or cast a legendary spell, you draw a card and you lose 1 life.";
    }

}