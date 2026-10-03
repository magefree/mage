package mage.cards.s;

import java.util.UUID;

import mage.abilities.Mode;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ToughnessPredicate;
import mage.target.TargetPermanent;

/**
 *
 * @author muz
 */
public final class SurgicalPrecision extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with toughness 4 or greater");

    static {
        filter.add(new ToughnessPredicate(ComparisonType.OR_GREATER, 4));
    }

    public SurgicalPrecision(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{W}");

        // Choose one --
        // * Destroy target creature with toughness 4 or greater. You gain 1 life.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
        this.getSpellAbility().addEffect(new GainLifeEffect(1));

        // * You draw a card and gain 2 life.
        this.getSpellAbility().addMode(new Mode(
            new DrawCardSourceControllerEffect(1, true)
        ).addEffect(new GainLifeEffect(2).setText("and gain 2 life")));
    }

    private SurgicalPrecision(final SurgicalPrecision card) {
        super(card);
    }

    @Override
    public SurgicalPrecision copy() {
        return new SurgicalPrecision(this);
    }
}
