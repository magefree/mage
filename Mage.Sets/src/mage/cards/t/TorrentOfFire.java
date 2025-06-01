
package mage.cards.t;

import mage.abilities.dynamicvalue.common.GreatestAmongPermanentsValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author nigelzor
 */
public final class TorrentOfFire extends CardImpl {

    public TorrentOfFire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}{R}");

        // Torrent of Fire deals damage equal to the greatest converted mana cost among permanents you control to any target.
        this.getSpellAbility().addEffect(new DamageTargetEffect(GreatestAmongPermanentsValue.MANAVALUE_CONTROLLED_PERMANENTS)
                .setText("{this} deals damage to any target equal to the greatest mana value among permanents you control.")
        );
        this.getSpellAbility().addHint(GreatestAmongPermanentsValue.MANAVALUE_CONTROLLED_PERMANENTS.getHint());
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
