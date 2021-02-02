package mage.cards.s;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.ElfKnightToken;
import mage.target.TargetPermanent;

/**
 *
 * @author TheElk801
 */
public final class SproutingRenewal extends CardImpl {

    public SproutingRenewal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // Convoke
        this.addAbility(new ConvokeAbility());

        // Choose one —
        // • Create a 2/2 green and white Elf Knight creature token with vigilance.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new ElfKnightToken()));

        // • Destroy target artifact or enchantment.
        Mode mode = new Mode(new DestroyTargetEffect());
        mode.addTarget(new TargetPermanent(
                StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT
        ));
        this.getSpellAbility().addMode(mode);
    }

    private SproutingRenewal(final SproutingRenewal card) {
        super(card);
    }

    @Override
    public SproutingRenewal copy() {
        return new SproutingRenewal(this);
    }
}
