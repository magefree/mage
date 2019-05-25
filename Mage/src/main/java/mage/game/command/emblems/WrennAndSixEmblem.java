package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.RetraceAbility;
import mage.cards.Card;
import mage.constants.*;
import mage.game.Game;
import mage.game.command.Emblem;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WrennAndSixEmblem extends Emblem {

    public WrennAndSixEmblem() {
        this.setName("Emblem Wrenn");
        this.getAbilities().add(new SimpleStaticAbility(Zone.COMMAND, new WrennAndSixEmblemEffect()));
        this.setExpansionSetCodeForImage("MH1");
    }
}

class WrennAndSixEmblemEffect extends ContinuousEffectImpl {
    WrennAndSixEmblemEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "Instant and sorcery cards in your graveyard have retrace.";
    }

    private WrennAndSixEmblemEffect(final WrennAndSixEmblemEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        for (UUID cardId : controller.getGraveyard()) {
            Card card = game.getCard(cardId);
            if (card == null || !card.isInstantOrSorcery()) {
                continue;
            }
            Ability ability = new RetraceAbility(card);
            ability.setSourceId(cardId);
            ability.setControllerId(card.getOwnerId());
            game.getState().addOtherAbility(card, ability);
        }
        return true;
    }

    @Override
    public WrennAndSixEmblemEffect copy() {
        return new WrennAndSixEmblemEffect(this);
    }
}
