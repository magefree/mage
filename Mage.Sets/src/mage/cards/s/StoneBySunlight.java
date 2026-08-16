package mage.cards.s;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.AddCardTypeTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author muz
 */
public final class StoneBySunlight extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with power 4 or greater");

    static {
        filter.add(new PowerPredicate(ComparisonType.OR_GREATER, 4));
    }

    public StoneBySunlight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Choose one --
        // * Destroy target creature with power 4 or greater.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));

        // * Until end of turn, target creature becomes an artifact in addition to its other types and gains indestructible.
        this.getSpellAbility().addMode(new Mode(
            new AddCardTypeTargetEffect(Duration.EndOfTurn, CardType.ARTIFACT)
                .setText("until end of turn, target creature becomes an artifact in addition to its other types"))
            .addEffect(new GainAbilityTargetEffect(IndestructibleAbility.getInstance())
                .setText("and gains indestructible"))
            .addTarget(new TargetCreaturePermanent()
        ));
    }

    private StoneBySunlight(final StoneBySunlight card) {
        super(card);
    }

    @Override
    public StoneBySunlight copy() {
        return new StoneBySunlight(this);
    }
}
