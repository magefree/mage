package mage.cards.p;

import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterAttackingOrBlockingCreature;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PiercingLight extends CardImpl {

    private static final FilterPermanent filter = new FilterAttackingOrBlockingCreature();

    public PiercingLight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Piercing Light deals 2 damage to target attacking or blocking creature. Scry 1.
        this.getSpellAbility().addEffect(new DamageTargetEffect(2));
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
        this.getSpellAbility().addEffect(new ScryEffect(1));
    }

    private PiercingLight(final PiercingLight card) {
        super(card);
    }

    @Override
    public PiercingLight copy() {
        return new PiercingLight(this);
    }
}
