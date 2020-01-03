package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.MayTapOrUntapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.common.TargetNonlandPermanent;
import mage.watchers.common.SpellsCastWatcher;

import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StingingLionfish extends CardImpl {

    public StingingLionfish(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.FISH);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever you cast your first spell during each opponent's turn, you may tap or untap target nonland permanent.
        this.addAbility(new StingingLionfishTriggeredAbility(), new SpellsCastWatcher());
    }

    private StingingLionfish(final StingingLionfish card) {
        super(card);
    }

    @Override
    public StingingLionfish copy() {
        return new StingingLionfish(this);
    }
}

class StingingLionfishTriggeredAbility extends SpellCastControllerTriggeredAbility {

    StingingLionfishTriggeredAbility() {
        super(new MayTapOrUntapTargetEffect(), false);
        this.addTarget(new TargetNonlandPermanent());
    }

    private StingingLionfishTriggeredAbility(StingingLionfishTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public StingingLionfishTriggeredAbility copy() {
        return new StingingLionfishTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getActivePlayerId().equals(this.getControllerId()) // ignore controller turn
                || !super.checkTrigger(event, game)) {
            return false;
        }

        if (!game.getOpponents(this.getControllerId()).contains(game.getActivePlayerId())) {
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
        return "Whenever you cast your first spell during each opponent's turn, you mat tap or untap target permanent.";
    }
}