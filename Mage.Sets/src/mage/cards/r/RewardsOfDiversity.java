
package mage.cards.r;

import java.util.UUID;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.MulticoloredPredicate;

/**
 *
 * @author LoneFox
 */
public final class RewardsOfDiversity extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a multicolored spell");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
        filter.add(MulticoloredPredicate.instance);
    }

    public RewardsOfDiversity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // Whenever an opponent casts a multicolored spell, you gain 4 life.
        this.addAbility(new SpellCastOpponentTriggeredAbility(
                new GainLifeEffect(4), filter, false));
    }

    private RewardsOfDiversity(final RewardsOfDiversity card) {
        super(card);
    }

    @Override
    public RewardsOfDiversity copy() {
        return new RewardsOfDiversity(this);
    }
}
