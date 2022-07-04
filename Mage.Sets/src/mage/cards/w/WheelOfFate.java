
package mage.cards.w;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardAllEffect;
import mage.abilities.effects.common.discard.DiscardHandAllEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author anonymous
 */
public final class WheelOfFate extends CardImpl {

    public WheelOfFate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "");

        this.color.setRed(true);

        // Suspend 4-{1}{R}
        this.addAbility(new SuspendAbility(4, new ManaCostsImpl<>("{1}{R}"), this));
        // Each player discards their hand, then draws seven cards.
        this.getSpellAbility().addEffect(new DiscardHandAllEffect());
        this.getSpellAbility().addEffect(new DrawCardAllEffect(7).setText(", then draws seven cards"));
    }

    private WheelOfFate(final WheelOfFate card) {
        super(card);
    }

    @Override
    public WheelOfFate copy() {
        return new WheelOfFate(this);
    }
}
