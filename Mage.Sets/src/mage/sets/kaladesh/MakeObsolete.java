package mage.sets.kaladesh;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostOpponentsEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;

/**
 * @author royk
 */
public class MakeObsolete extends CardImpl {

    public MakeObsolete(UUID ownerId) {
        super(ownerId, 89, "Make Obsolete", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{2}{B}");
        this.expansionSetCode = "KLD";

        // Creatures your opponents control get -1/-1  until end of turn
        this.getSpellAbility().addEffect(new BoostOpponentsEffect(-1, -1, Duration.EndOfTurn));
    }

    public MakeObsolete(final MakeObsolete card) {
        super(card);
    }

    @Override
    public MakeObsolete copy() {
        return new MakeObsolete(this);
    }
}
