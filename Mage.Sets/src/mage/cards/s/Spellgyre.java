package mage.cards.s;

import mage.abilities.Mode;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Spellgyre extends CardImpl {

    public Spellgyre(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}{U}");

        // Choose one --
        // * Counter target spell.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell());

        // * Surveil 2, then draw two cards.
        this.getSpellAbility().addMode(new Mode(new SurveilEffect(2, false))
                .addEffect(new DrawCardSourceControllerEffect(2).concatBy(", then")));
    }

    private Spellgyre(final Spellgyre card) {
        super(card);
    }

    @Override
    public Spellgyre copy() {
        return new Spellgyre(this);
    }
}
