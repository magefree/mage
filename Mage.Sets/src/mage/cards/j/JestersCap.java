package mage.cards.j;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.search.SearchLibraryAndExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class JestersCap extends CardImpl {

    public JestersCap(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // {2}, {tap}, Sacrifice Jester's Cap: Search target player's library for three cards and exile them. Then that player shuffles their library.
        Ability ability = new SimpleActivatedAbility(
                new SearchLibraryAndExileTargetEffect(3, false), new GenericManaCost(2)
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private JestersCap(final JestersCap card) {
        super(card);
    }

    @Override
    public JestersCap copy() {
        return new JestersCap(this);
    }
}
