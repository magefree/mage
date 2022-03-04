package mage.cards.p;

import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class PiratesPillage extends CardImpl {

    public PiratesPillage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}");

        // As an additional cost to cast Pirate's Pillage, discard a card.
        this.getSpellAbility().addCost(new DiscardCardCost(false));

        // Draw two cards and create two colorless Treasure artifacts with "{T}, Sacrifice this artifact: Add one mana of any color."
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2));
        this.getSpellAbility().addEffect(new CreateTokenEffect(new TreasureToken(), 2).concatBy("and"));
    }

    private PiratesPillage(final PiratesPillage card) {
        super(card);
    }

    @Override
    public PiratesPillage copy() {
        return new PiratesPillage(this);
    }
}
