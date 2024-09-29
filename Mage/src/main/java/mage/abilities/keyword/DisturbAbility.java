package mage.abilities.keyword;

import mage.abilities.common.SpellTransformedAbility;
import mage.cards.Card;
import mage.constants.*;
import mage.game.Game;

import java.util.UUID;

/**
 * 702.146. Disturb
 * <p>
 * 702.146a Disturb is an ability found on the front face of some transforming double-faced cards
 * (see rule 712, “Double-Faced Cards”). “Disturb [cost]” means “You may cast this card
 * transformed from your graveyard by paying [cost] rather than its mana cost.” See
 * rule 712.4b.
 * <p>
 * 702.146b A resolving transforming double-faced spell that was cast using its disturb
 * ability enters the battlefield with its back face up.
 *
 * @author notgreat, weirddan455, JayDi85
 */
public class DisturbAbility extends SpellTransformedAbility {
    public DisturbAbility(Card card, String manaCost) {
        super(card, manaCost);

        this.zone = Zone.GRAVEYARD;
        this.setSpellAbilityCastMode(SpellAbilityCastMode.DISTURB);
    }

    private DisturbAbility(final DisturbAbility ability) {
        super(ability);
    }

    @Override
    public DisturbAbility copy() {
        return new DisturbAbility(this);
    }

    @Override
    public ActivationStatus canActivate(UUID playerId, Game game) {
        if (super.canActivate(playerId, game).canActivate()) {
            Card card = game.getCard(getSourceId());
            if (card != null && game.getState().getZone(card.getId()) == Zone.GRAVEYARD) {
                return card.getSpellAbility().canActivate(playerId, game);
            }
        }
        return ActivationStatus.getFalse();
    }

    @Override
    public String getRule(boolean all) {
        return this.getRule();
    }

    @Override
    public String getRule() {
        return "Disturb " + this.manaCost
                + " <i>(You may cast this card transformed from your graveyard for its disturb cost.)</i>";
    }
}
