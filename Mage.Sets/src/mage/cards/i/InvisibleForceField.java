package mage.cards.i;

import java.util.UUID;

import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.ReboundAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledPermanent;
import mage.target.TargetPermanent;

/**
 *
 * @author muz
 */
public final class InvisibleForceField extends CardImpl {

    private static final FilterControlledPermanent filter
        = new FilterControlledPermanent("permanents you control");

    public InvisibleForceField(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Up to four target permanents you control gain indestructible until end of turn.
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(IndestructibleAbility.getInstance()));
        this.getSpellAbility().addTarget(new TargetPermanent(0, 4, filter));

        // Rebound
        this.addAbility(new ReboundAbility());
    }

    private InvisibleForceField(final InvisibleForceField card) {
        super(card);
    }

    @Override
    public InvisibleForceField copy() {
        return new InvisibleForceField(this);
    }
}
