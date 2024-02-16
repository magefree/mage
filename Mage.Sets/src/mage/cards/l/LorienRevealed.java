package mage.cards.l;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.IslandcyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LorienRevealed extends CardImpl {

    public LorienRevealed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}{U}");

        // Draw three cards.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(3));

        // Islandcycling {1}
        this.addAbility(new IslandcyclingAbility(new ManaCostsImpl<>("{1}")));
    }

    private LorienRevealed(final LorienRevealed card) {
        super(card);
    }

    @Override
    public LorienRevealed copy() {
        return new LorienRevealed(this);
    }
}
