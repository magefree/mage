
package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.keyword.DelveAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public final class DeadDrop extends CardImpl {

    public DeadDrop(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{9}{B}");

        // Delve
        this.addAbility(new DelveAbility());
        // Target player sacrifices two creatures
        this.getSpellAbility().addEffect(new SacrificeEffect(StaticFilters.FILTER_PERMANENT_CREATURES, 2, "Target player"));
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private DeadDrop(final DeadDrop card) {
        super(card);
    }

    @Override
    public DeadDrop copy() {
        return new DeadDrop(this);
    }
}
