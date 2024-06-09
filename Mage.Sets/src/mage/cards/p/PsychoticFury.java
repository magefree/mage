
package mage.cards.p;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.MulticoloredPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author KholdFuzion
 */
public final class PsychoticFury extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("multicolored creature");

    static {
        filter.add(MulticoloredPredicate.instance);
    }

    public PsychoticFury(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{R}");


        // Target multicolored creature gains double strike until end of turn.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(DoubleStrikeAbility.getInstance(), Duration.EndOfTurn));
        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private PsychoticFury(final PsychoticFury card) {
        super(card);
    }

    @Override
    public PsychoticFury copy() {
        return new PsychoticFury(this);
    }
}
