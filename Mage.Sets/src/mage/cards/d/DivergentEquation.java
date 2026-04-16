package mage.cards.d;

import java.util.UUID;

import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetadjustment.XTargetsCountAdjuster;

/**
 *
 * @author muz
 */
public final class DivergentEquation extends CardImpl {

    private static final FilterInstantOrSorceryCard filter =
         new FilterInstantOrSorceryCard("instant and/or sorcery cards");

    public DivergentEquation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{X}{U}");

        // Return up to X target instant and/or sorcery cards from your graveyard to your hand.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect()
            .setText("Return X target instant and/or sorcery cards from your graveyard to your hand")
        );
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(0, 1, filter));
        this.getSpellAbility().setTargetAdjuster(new XTargetsCountAdjuster());

        // Exile Divergent Equation.
        this.getSpellAbility().addEffect(new ExileSpellEffect());
    }

    private DivergentEquation(final DivergentEquation card) {
        super(card);
    }

    @Override
    public DivergentEquation copy() {
        return new DivergentEquation(this);
    }
}
