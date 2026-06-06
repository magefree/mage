package mage.abilities.effects.common.asthought;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.cards.Card;
import mage.constants.*;
import mage.game.Game;

import java.util.UUID;

/**
 * @author Susucr
 */
public class MayCastFromGraveyardAsAdventureEffect extends AsThoughEffectImpl {

    public MayCastFromGraveyardAsAdventureEffect() {
        super(AsThoughEffectType.CAST_FROM_NOT_OWN_HAND_ZONE, Duration.UntilEndOfYourNextTurn, Outcome.Benefit);
        staticText = "you may cast it from your graveyard as an Adventure until the end of your next turn";
    }

    private MayCastFromGraveyardAsAdventureEffect(final MayCastFromGraveyardAsAdventureEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public MayCastFromGraveyardAsAdventureEffect copy() {
        return new MayCastFromGraveyardAsAdventureEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability affectedAbility, Ability source, Game game, UUID affectedControllerId) {
        if (!source.isControlledBy(affectedControllerId) || !(affectedAbility instanceof SpellAbility)) {
            return false;
        }
        if (((SpellAbility) affectedAbility).getSpellAbilityType() != SpellAbilityType.ADVENTURE_OMEN_RIGHT) {
            return false;
        }
        Card card = game.getCard(affectedAbility.getSourceId());
        Card sourceCard = game.getCard(source.getSourceId());
        if (card == null || sourceCard == null) {
            return false;
        }
        // should have the same main card id
        if (!card.getMainCard().getId().equals(sourceCard.getMainCard().getId())) {
            return false;
        }

        return game.getState().getZone(source.getSourceId()) == Zone.GRAVEYARD && source.getStackMomentSourceZCC() == sourceCard.getZoneChangeCounter(game);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        throw new IllegalArgumentException("Wrong code usage: can't call applies method on empty affectedAbility");
    }
}
