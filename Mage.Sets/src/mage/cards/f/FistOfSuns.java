
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceIsSpellCondition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.continuous.WUBRGInsteadEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class FistOfSuns extends CardImpl {

    public FistOfSuns(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // You may pay {W}{U}{B}{R}{G} rather than pay the mana cost for spells that you cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new WUBRGInsteadEffect()));
    }

    public FistOfSuns(final FistOfSuns card) {
        super(card);
    }

    @Override
    public FistOfSuns copy() {
        return new FistOfSuns(this);
    }
        
}
