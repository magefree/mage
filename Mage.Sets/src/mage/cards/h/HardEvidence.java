package mage.cards.h;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.CrabToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HardEvidence extends CardImpl {

    public HardEvidence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{U}");

        // Create a 0/3 blue Crab creature token.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new CrabToken()));

        // Investigate.
        this.getSpellAbility().addEffect(new InvestigateEffect().concatBy("<br>"));
    }

    private HardEvidence(final HardEvidence card) {
        super(card);
    }

    @Override
    public HardEvidence copy() {
        return new HardEvidence(this);
    }
}
