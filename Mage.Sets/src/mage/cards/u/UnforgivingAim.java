package mage.cards.u;

import java.util.UUID;

import mage.abilities.Mode;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.BlackGreenElfToken;
import mage.target.TargetPermanent;
import mage.target.common.TargetEnchantmentPermanent;

/**
 *
 * @author muz
 */
public final class UnforgivingAim extends CardImpl {

    public UnforgivingAim(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}");

        // Choose one --
        // * Destroy target creature with flying.
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_FLYING));
        this.getSpellAbility().addEffect(new DestroyTargetEffect());

        // * Destroy target enchantment.
        this.getSpellAbility().addMode(new Mode(new DestroyTargetEffect()).addTarget(new TargetEnchantmentPermanent()));

        // * Create a 2/2 black and green Elf creature token.
        this.getSpellAbility().addMode(new Mode(new CreateTokenEffect(new BlackGreenElfToken())));
    }

    private UnforgivingAim(final UnforgivingAim card) {
        super(card);
    }

    @Override
    public UnforgivingAim copy() {
        return new UnforgivingAim(this);
    }
}
