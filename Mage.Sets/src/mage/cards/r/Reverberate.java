package mage.cards.r;

import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class Reverberate extends CardImpl {

    public Reverberate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}{R}");

        // Copy target instant or sorcery spell. You may choose new targets for the copy.
        this.getSpellAbility().addTarget(new TargetSpell(StaticFilters.FILTER_SPELL_INSTANT_OR_SORCERY));
        this.getSpellAbility().addEffect(new CopyTargetSpellEffect());
    }

    private Reverberate(final Reverberate card) {
        super(card);
    }

    @Override
    public Reverberate copy() {
        return new Reverberate(this);
    }
}
