
package mage.cards.v;

import java.util.UUID;
import mage.abilities.effects.common.CounterTargetWithReplacementEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.target.TargetSpell;

/**
 *
 * @author fireshoes
 */
public final class VoidShatter extends CardImpl {

    public VoidShatter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}{U}");

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // Counter target spell. If that spell is countered this way, exile it instead of putting it into its owner's graveyard.
        this.getSpellAbility().addEffect(new CounterTargetWithReplacementEffect(PutCards.EXILED));
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private VoidShatter(final VoidShatter card) {
        super(card);
    }

    @Override
    public VoidShatter copy() {
        return new VoidShatter(this);
    }
}
