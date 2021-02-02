
package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.abilities.keyword.BuybackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetSpell;

/**
 *
 * @author fireshoes
 */
public final class Reiterate extends CardImpl {

    public Reiterate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}{R}");

        // Buyback {3}
        this.addAbility(new BuybackAbility("{3}"));

        // Copy target instant or sorcery spell. You may choose new targets for the copy.
        this.getSpellAbility().addTarget(new TargetSpell(StaticFilters.FILTER_SPELL_INSTANT_OR_SORCERY));
        this.getSpellAbility().addEffect(new CopyTargetSpellEffect());
    }

    private Reiterate(final Reiterate card) {
        super(card);
    }

    @Override
    public Reiterate copy() {
        return new Reiterate(this);
    }
}
