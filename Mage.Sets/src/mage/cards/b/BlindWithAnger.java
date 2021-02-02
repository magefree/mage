
package mage.cards.b;

import java.util.UUID;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author Loki
 */
public final class BlindWithAnger extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nonlegendary creature");

    static {
        filter.add(Predicates.not(SuperType.LEGENDARY.getPredicate()));
    }

    public BlindWithAnger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{R}");
        this.subtype.add(SubType.ARCANE);

        this.getSpellAbility().addEffect(new UntapTargetEffect().setText("Untap target nonlegendary creature"));
        this.getSpellAbility().addEffect(new GainControlTargetEffect(Duration.EndOfTurn).setText("and gain control of it until end of turn"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn).setText("It gains haste until end of turn."));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
    }

    private BlindWithAnger(final BlindWithAnger card) {
        super(card);
    }

    @Override
    public BlindWithAnger copy() {
        return new BlindWithAnger(this);
    }

}
