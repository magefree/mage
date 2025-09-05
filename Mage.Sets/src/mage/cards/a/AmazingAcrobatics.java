package mage.cards.a;

import mage.abilities.Mode;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 *
 * @author Jmlundeen
 */
public final class AmazingAcrobatics extends CardImpl {

    public AmazingAcrobatics(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}{U}");
        

        // Choose one or both --
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(2);

        // * Counter target spell.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell());

        // * Tap one or two target creatures.
        Mode mode = new Mode(new TapTargetEffect());
        mode.addTarget(new TargetPermanent(1, 2, StaticFilters.FILTER_PERMANENT_CREATURES));
        this.getSpellAbility().getModes().addMode(mode);
    }

    private AmazingAcrobatics(final AmazingAcrobatics card) {
        super(card);
    }

    @Override
    public AmazingAcrobatics copy() {
        return new AmazingAcrobatics(this);
    }
}
