package mage.cards.l;

import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.AffinityForArtifactsAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAttackingOrBlockingCreature;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LensFlare extends CardImpl {

    public LensFlare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{W}");

        // Affinity for artifacts
        this.addAbility(new AffinityForArtifactsAbility());

        // Lens Flare deals 5 damage to target attacking or blocking creature.
        this.getSpellAbility().addEffect(new DamageTargetEffect(5));
        this.getSpellAbility().addTarget(new TargetAttackingOrBlockingCreature());
    }

    private LensFlare(final LensFlare card) {
        super(card);
    }

    @Override
    public LensFlare copy() {
        return new LensFlare(this);
    }
}
