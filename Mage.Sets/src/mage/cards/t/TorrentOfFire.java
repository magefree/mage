
package mage.cards.t;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.HighestManaValueCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author nigelzor
 */
public final class TorrentOfFire extends CardImpl {

    public TorrentOfFire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}{R}");

        // Torrent of Fire deals damage equal to the highest converted mana cost among permanents you control to any target.
        this.getSpellAbility().addEffect(new DamageTargetEffect(new HighestManaValueCount())
                .setText("{this} deals damage to any target equal to the highest mana value among permanents you control.")
        );
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private TorrentOfFire(final TorrentOfFire card) {
        super(card);
    }

    @Override
    public TorrentOfFire copy() {
        return new TorrentOfFire(this);
    }
}
