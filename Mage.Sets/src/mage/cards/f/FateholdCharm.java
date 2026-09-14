package mage.cards.f;

import java.util.UUID;

import mage.abilities.Mode;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.keyword.EmpowerJaceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterSpellOrPermanent;
import mage.target.common.TargetSpellOrPermanent;

/**
 *
 * @author muz
 */
public final class FateholdCharm extends CardImpl {

    private static final FilterSpellOrPermanent filter = new FilterSpellOrPermanent("spell or creature");

    static {
        filter.setPermanentFilter(new FilterCreaturePermanent());
    }

    public FateholdCharm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}{U}");
        
        // Choose one --
        // * Draw a card. Empower Jace 2.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));
        this.getSpellAbility().addEffect(new EmpowerJaceEffect(2));

        // * Return target spell or creature to its owner's hand.
        this.getSpellAbility().addMode(new Mode(
            new ReturnToHandTargetEffect()).addTarget(
                new TargetSpellOrPermanent(1, 1, filter, false)
            )
        );

        // * Creatures you control get +1/+2 until end of turn.
        this.getSpellAbility().addMode(new Mode(
            new BoostControlledEffect(1, 2, Duration.EndOfTurn)
        ));
    }

    private FateholdCharm(final FateholdCharm card) {
        super(card);
    }

    @Override
    public FateholdCharm copy() {
        return new FateholdCharm(this);
    }
}
