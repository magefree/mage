
package mage.cards.l;

import java.util.UUID;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetPlayerOrPlaneswalker;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class LavaAxe extends CardImpl {

    public LavaAxe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{R}");

        this.getSpellAbility().addTarget(new TargetPlayerOrPlaneswalker());
        this.getSpellAbility().addEffect(new DamageTargetEffect(5));
    }

    private LavaAxe(final LavaAxe card) {
        super(card);
    }

    @Override
    public LavaAxe copy() {
        return new LavaAxe(this);
    }
}
