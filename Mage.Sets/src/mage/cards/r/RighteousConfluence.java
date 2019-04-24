
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.KnightToken;
import mage.target.common.TargetEnchantmentPermanent;

/**
 *
 * @author LevelX2
 */
public final class RighteousConfluence extends CardImpl {

    public RighteousConfluence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{W}{W}");

        // Choose three - You may choose the same mode more than once.
        this.getSpellAbility().getModes().setMinModes(3);
        this.getSpellAbility().getModes().setMaxModes(3);
        this.getSpellAbility().getModes().setEachModeMoreThanOnce(true);

        // - Create a 2/2 white Knight creature token with vigilance;
        this.getSpellAbility().addEffect(new CreateTokenEffect(new KnightToken()));

        //  - Exile target enchantment;
        Mode mode = new Mode();
        mode.getEffects().add(new ExileTargetEffect());
        mode.getTargets().add(new TargetEnchantmentPermanent());
        this.getSpellAbility().getModes().addMode(mode);

        // You gain 5 life;
        mode = new Mode();
        mode.getEffects().add(new GainLifeEffect(5));
        this.getSpellAbility().getModes().addMode(mode);
    }

    public RighteousConfluence(final RighteousConfluence card) {
        super(card);
    }

    @Override
    public RighteousConfluence copy() {
        return new RighteousConfluence(this);
    }
}
