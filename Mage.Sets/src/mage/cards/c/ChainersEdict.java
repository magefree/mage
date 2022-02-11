
package mage.cards.c;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TimingRule;
import mage.filter.StaticFilters;
import mage.target.TargetPlayer;

/**
 *
 * @author dustinconrad
 */
public final class ChainersEdict extends CardImpl {

    public ChainersEdict(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Target player sacrifices a creature.
        this.getSpellAbility().addEffect(new SacrificeEffect(StaticFilters.FILTER_PERMANENT_CREATURE, 1, "Target player"));
        this.getSpellAbility().addTarget(new TargetPlayer());

        // Flashback {5}{B}{B}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl("{5}{B}{B}")));
    }

    private ChainersEdict(final ChainersEdict card) {
        super(card);
    }

    @Override
    public ChainersEdict copy() {
        return new ChainersEdict(this);
    }
}
