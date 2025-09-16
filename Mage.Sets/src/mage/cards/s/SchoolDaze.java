package mage.cards.s;

import mage.abilities.Mode;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SchoolDaze extends CardImpl {

    public SchoolDaze(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}{U}");

        // Choose one --
        // * Do Homework -- Draw three cards.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(3));
        this.getSpellAbility().withFirstModeFlavorWord("Do Homework");

        // * Fight Crime -- Counter target spell. Draw a card.
        this.getSpellAbility().addMode(new Mode(new CounterTargetEffect())
                .addEffect(new DrawCardSourceControllerEffect(1))
                .addTarget(new TargetSpell())
                .withFlavorWord("Fight Crime"));
    }

    private SchoolDaze(final SchoolDaze card) {
        super(card);
    }

    @Override
    public SchoolDaze copy() {
        return new SchoolDaze(this);
    }
}
