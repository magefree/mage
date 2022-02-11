package mage.cards.g;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.RegenerateAllEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.target.common.TargetEnchantmentPermanent;

/**
 *
 * @author LevelX2
 */
public final class GolgariCharm extends CardImpl {

    public GolgariCharm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{B}{G}");


        // Choose one — All creatures get -1/-1 until end of turn;
        this.getSpellAbility().addEffect(new BoostAllEffect(-1, -1, Duration.EndOfTurn));

        // or destroy target enchantment;
        Mode mode = new Mode();
        mode.addEffect(new DestroyTargetEffect());
        mode.addTarget(new TargetEnchantmentPermanent());
        this.getSpellAbility().addMode(mode);

        // or regenerate each creature you control.
        mode = new Mode();
        mode.addEffect(new RegenerateAllEffect(new FilterControlledCreaturePermanent()));
        this.getSpellAbility().addMode(mode);
    }

    private GolgariCharm(final GolgariCharm card) {
        super(card);
    }

    @Override
    public GolgariCharm copy() {
        return new GolgariCharm(this);
    }
}
