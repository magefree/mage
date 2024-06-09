
package mage.cards.s;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.permanent.token.SkeletonRegenerateToken;

/**
 *
 * @author Loki
 */
public final class SpawningPool extends CardImpl {

    public SpawningPool(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");
        this.addAbility(new EntersBattlefieldTappedAbility());
        this.addAbility(new BlackManaAbility());
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesCreatureSourceEffect(new SkeletonRegenerateToken(), CardType.LAND, Duration.EndOfTurn), new ManaCostsImpl<>("{1}{B}")));
    }

    private SpawningPool(final SpawningPool card) {
        super(card);
    }

    @Override
    public SpawningPool copy() {
        return new SpawningPool(this);
    }
}
