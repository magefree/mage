
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetAttackingCreature;

/**
 *
 * @author fireshoes
 */
public final class AnointerOfChampions extends CardImpl {

    public AnointerOfChampions(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {T}: Target attacking creature gets +1/+1 until end of turn.
        Ability ability = new SimpleActivatedAbility(new BoostTargetEffect(1, 1, Duration.EndOfTurn), new TapSourceCost());
        ability.addTarget(new TargetAttackingCreature());
        this.addAbility(ability);
    }

    private AnointerOfChampions(final AnointerOfChampions card) {
        super(card);
    }

    @Override
    public AnointerOfChampions copy() {
        return new AnointerOfChampions(this);
    }
}
