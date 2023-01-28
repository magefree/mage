package mage.cards.a;

import java.util.UUID;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author AhmadYProjects
 */
public final class AnnihilatingGlare extends CardImpl {

    public AnnihilatingGlare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}");
        

        // As an additional cost to cast this spell, pay {4} or sacrifice an artifact or creature.
        // Destroy target creature or planeswalker.
    }

    private AnnihilatingGlare(final AnnihilatingGlare card) {
        super(card);
    }

    @Override
    public AnnihilatingGlare copy() {
        return new AnnihilatingGlare(this);
    }
}
