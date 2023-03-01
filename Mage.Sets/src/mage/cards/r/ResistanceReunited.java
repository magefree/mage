package mage.cards.r;

import java.util.UUID;

import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.EquippedPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author TheElk801
 */
public final class ResistanceReunited extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("equipped creatures");

    static {
        filter.add(EquippedPredicate.instance);
    }

    public ResistanceReunited(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Target creature gets +2/+2 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(2, 2));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Equipped creatures you control gain indestructible until end of turn.
        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn, filter
        ).concatBy("<br>"));
    }

    private ResistanceReunited(final ResistanceReunited card) {
        super(card);
    }

    @Override
    public ResistanceReunited copy() {
        return new ResistanceReunited(this);
    }
}
