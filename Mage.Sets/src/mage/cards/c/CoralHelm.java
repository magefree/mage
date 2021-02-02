
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author nigelzor
 */
public final class CoralHelm extends CardImpl {

    public CoralHelm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // {3}, Discard a card at random: Target creature gets +2/+2 until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostTargetEffect(2, 2, Duration.EndOfTurn), new GenericManaCost(3));
        ability.addCost(new DiscardCardCost(true));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private CoralHelm(final CoralHelm card) {
        super(card);
    }

    @Override
    public CoralHelm copy() {
        return new CoralHelm(this);
    }
}
