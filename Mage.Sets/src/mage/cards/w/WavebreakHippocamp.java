package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.watchers.common.SpellsCastWatcher;

import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WavebreakHippocamp extends CardImpl {

    public WavebreakHippocamp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.HORSE);
        this.subtype.add(SubType.FISH);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you cast your first spell during each opponent's turn, draw a card.
        this.addAbility(new WavebreakHippocampTriggeredAbility());
    }

    private WavebreakHippocamp(final WavebreakHippocamp card) {
        super(card);
    }

    @Override
    public WavebreakHippocamp copy() {
        return new WavebreakHippocamp(this);
    }
}

class WavebreakHippocampTriggeredAbility extends SpellCastControllerTriggeredAbility {

    WavebreakHippocampTriggeredAbility() {
        super(new DrawCardSourceControllerEffect(1), false);
    }

    private WavebreakHippocampTriggeredAbility(WavebreakHippocampTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public WavebreakHippocampTriggeredAbility copy() {
        return new WavebreakHippocampTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!game.getActivePlayerId().equals(this.getControllerId())
                || !super.checkTrigger(event, game)) {
            return false;
        }
        SpellsCastWatcher watcher = game.getState().getWatcher(SpellsCastWatcher.class);
        if (watcher == null) {
            return false;
        }
        List<Spell> spells = watcher.getSpellsCastThisTurn(event.getPlayerId());
        return spells != null && spells.size() == 1;
    }

    @Override
    public String getRule() {
        return "Whenever you cast your first spell during each opponent's turn, draw a card.";
    }
}