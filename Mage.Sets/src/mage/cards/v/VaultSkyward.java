package mage.cards.v;

import java.util.UUID;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class VaultSkyward extends CardImpl {

    public VaultSkyward (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}");

        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(FlyingAbility.getInstance(), Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new UntapTargetEffect().setText("Untap it"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private VaultSkyward(final VaultSkyward card) {
        super(card);
    }

    @Override
    public VaultSkyward copy() {
        return new VaultSkyward(this);
    }

}
