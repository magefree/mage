package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.MageSingleton;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.AdventureCardSpell;
import mage.cards.Card;
import mage.constants.AsThoughEffectType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author phulin
 */
public class ExileAdventureSpellEffect extends OneShotEffect implements MageSingleton {

    private static final ExileAdventureSpellEffect instance = new ExileAdventureSpellEffect();

    public static ExileAdventureSpellEffect getInstance() {
        return instance;
    }

    public static UUID adventureExileId(UUID controllerId, Game game) {
        return CardUtil.getExileZoneId(controllerId.toString() + "- On an Adventure", game);
    }

    private ExileAdventureSpellEffect() {
        super(Outcome.Exile);
        staticText = "";
    }

    @Override
    public ExileAdventureSpellEffect copy() {
        return instance;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Spell spell = game.getStack().getSpell(source.getId());
            if (spell != null) {
                Card spellCard = spell.getCard();
                if (spellCard instanceof AdventureCardSpell) {
                    UUID exileId = adventureExileId(controller.getId(), game);
                    game.getExile().createZone(exileId, "On an Adventure from " + controller.getName());
                    AdventureCardSpell adventureSpellCard = (AdventureCardSpell) spellCard;
                    Card parentCard = adventureSpellCard.getParentCard();
                    if (controller.moveCardsToExile(parentCard, source, game, true, exileId, "On an Adventure from " + controller.getName())) {
                        ContinuousEffect effect = new AdventureCastFromExileEffect();
                        effect.setTargetPointer(new FixedTarget(parentCard, game));
                        game.addEffect(effect, source);
                    }
                }
            }
            return true;
        }
        return false;
    }
}

class AdventureCastFromExileEffect extends AsThoughEffectImpl {

    public AdventureCastFromExileEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.Custom, Outcome.Benefit);
        staticText = "Then exile this card. You may cast the creature later from exile.";
    }

    protected AdventureCastFromExileEffect(final AdventureCastFromExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public AdventureCastFromExileEffect copy() {
        return new AdventureCastFromExileEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        UUID targetId = getTargetPointer().getFirst(game, source);
        ExileZone adventureExileZone = game.getExile().getExileZone(ExileAdventureSpellEffect.adventureExileId(affectedControllerId, game));
        if (targetId == null) {
            this.discard();
        } else if (objectId.equals(targetId)
                && affectedControllerId.equals(source.getControllerId())
                && adventureExileZone.contains(objectId)) {
            Card card = game.getCard(objectId);
            return card != null;
        }
        return false;
    }
}
