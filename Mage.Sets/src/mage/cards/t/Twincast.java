package mage.cards.t;

import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author Loki
 */
public final class Twincast extends CardImpl {

    public Twincast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}{U}");

        // Copy target instant or sorcery spell. You may choose new targets for the copy.
        this.getSpellAbility().addEffect(new CopyTargetSpellEffect());
        this.getSpellAbility().addTarget(new TargetSpell(StaticFilters.FILTER_SPELL_INSTANT_OR_SORCERY));
    }

    private Twincast(final Twincast card) {
        super(card);
    }

    @Override
    public Twincast copy() {
        return new Twincast(this);
    }
}
