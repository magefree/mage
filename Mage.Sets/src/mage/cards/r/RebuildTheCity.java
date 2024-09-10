package mage.cards.r;

import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetLandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RebuildTheCity extends CardImpl {

    public RebuildTheCity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}{R}{G}");

        // Choose target land. Create three tokens that are copies of it, except they're 3/3 creatures in addition to their other types and they have vigilance and menace.
        this.getSpellAbility().addEffect(new CreateTokenCopyTargetEffect(
                null, CardType.CREATURE, false, 3, false,
                false, null, 3, 3, false
        ).addAdditionalAbilities(
                VigilanceAbility.getInstance(), new MenaceAbility(false)
        ).setText("choose target land. Create three tokens that are copies of it, " +
                "except they're 3/3 creatures in addition to their other types and they have vigilance and menace"));
        this.getSpellAbility().addTarget(new TargetLandPermanent());
    }

    private RebuildTheCity(final RebuildTheCity card) {
        super(card);
    }

    @Override
    public RebuildTheCity copy() {
        return new RebuildTheCity(this);
    }
}
