
package mage.cards.u;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TimingRule;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

/**
 * @author nantuko
 */
public final class UnburialRites extends CardImpl {

    public UnburialRites(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}");

        // Return target creature card from your graveyard to the battlefield.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));

        // Flashback {3}{W}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl("{3}{W}")));
    }

    private UnburialRites(final UnburialRites card) {
        super(card);
    }

    @Override
    public UnburialRites copy() {
        return new UnburialRites(this);
    }
}
