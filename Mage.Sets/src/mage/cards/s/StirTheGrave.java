
package mage.cards.s;

import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetadjustment.XManaValueTargetAdjuster;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class StirTheGrave extends CardImpl {

    public StirTheGrave(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{B}");

        // Return target creature card with converted mana cost X or less from your graveyard to the battlefield.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect().setText("return target creature card with mana value X or less from your graveyard to the battlefield"));
        this.getSpellAbility().setTargetAdjuster(new XManaValueTargetAdjuster(ComparisonType.OR_LESS));
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
    }

    private StirTheGrave(final StirTheGrave card) {
        super(card);
    }

    @Override
    public StirTheGrave copy() {
        return new StirTheGrave(this);
    }
}
