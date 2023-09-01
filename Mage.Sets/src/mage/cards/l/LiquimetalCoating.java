

package mage.cards.l;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.continuous.AddCardTypeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.target.TargetPermanent;

/**
 *
 * @author Loki
 */
public final class LiquimetalCoating extends CardImpl {

    public LiquimetalCoating (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");

        // {T}: Target permanent becomes an artifact in addition to its other types until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCardTypeTargetEffect(Duration.EndOfTurn, CardType.ARTIFACT), new TapSourceCost());
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);
    }

    private LiquimetalCoating(final LiquimetalCoating card) {
        super(card);
    }

    @Override
    public LiquimetalCoating copy() {
        return new LiquimetalCoating(this);
    }

}
