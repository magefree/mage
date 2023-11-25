
package mage.cards.g;

import java.util.UUID;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPlayer;

/**
 *
 * @author Loki
 */
public final class GethsVerdict extends CardImpl {

    public GethsVerdict(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}{B}");

        this.getSpellAbility().addEffect(new SacrificeEffect(StaticFilters.FILTER_PERMANENT_CREATURE, 1, "Target player"));
        this.getSpellAbility().addEffect(new LoseLifeTargetEffect(1).setText("and loses 1 life"));
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private GethsVerdict(final GethsVerdict card) {
        super(card);
    }

    @Override
    public GethsVerdict copy() {
        return new GethsVerdict(this);
    }

}
