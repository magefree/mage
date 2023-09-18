
package mage.cards.i;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.ChooseNewTargetsTargetEffect;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetSpell;

/**
 *
 * @author fireshoes
 */
public final class InsidiousWill extends CardImpl {

    public InsidiousWill(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{U}{U}");

        // Choose one &mdash
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(1);

        // Counter target spell.;
        getSpellAbility().addEffect(new CounterTargetEffect());
        getSpellAbility().addTarget(new TargetSpell());

        // You may choose new targets for target spell.;
        Mode mode = new Mode(new ChooseNewTargetsTargetEffect());
        mode.addTarget(new TargetSpell());
        this.getSpellAbility().addMode(mode);

        // Copy target instant or sorcery spell. You may choose new targets for the copy.
        mode = new Mode(new CopyTargetSpellEffect());
        mode.addTarget(new TargetSpell(StaticFilters.FILTER_SPELL_INSTANT_OR_SORCERY));
        this.getSpellAbility().getModes().addMode(mode);
    }

    private InsidiousWill(final InsidiousWill card) {
        super(card);
    }

    @Override
    public InsidiousWill copy() {
        return new InsidiousWill(this);
    }
}
