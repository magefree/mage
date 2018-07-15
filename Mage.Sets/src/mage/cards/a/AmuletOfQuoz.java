package mage.cards.a;

import java.util.UUID;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.InfoEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author noahg
 */
public final class AmuletOfQuoz extends CardImpl {

    public AmuletOfQuoz(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{6}");
        

        // Remove Amulet of Quoz from your deck before playing if you're not playing for ante.
        // {T}, Sacrifice Amulet of Quoz: Target opponent may ante the top card of their library. If they don’t, you flip a coin. If you win the flip, that player loses the game. If you lose the flip, you lose the game. Activate this ability only during your upkeep.
        this.getSpellAbility().addEffect(new InfoEffect("Remove Amulet of Quoz from your deck before playing if " +
                "you're not playing for ante.\nSacrifice Amulet of Quoz: Target opponent may ante the top card of " +
                "their library. If they don’t, you flip a coin. If you win the flip, that player loses the game. If " +
                "you lose the flip, you lose the game. Activate this ability only during your upkeep."));
    }

    public AmuletOfQuoz(final AmuletOfQuoz card) {
        super(card);
    }

    @Override
    public AmuletOfQuoz copy() {
        return new AmuletOfQuoz(this);
    }
}
