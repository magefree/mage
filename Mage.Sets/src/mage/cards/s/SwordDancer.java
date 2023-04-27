
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.target.common.TargetAttackingCreature;

/**
 *
 * @author Plopman
 */
public final class SwordDancer extends CardImpl {

    public SwordDancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.REBEL);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {W}{W}: Target attacking creature gets -1/-0 until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostTargetEffect(-1, 0, Duration.EndOfTurn), new ManaCostsImpl<>("{W}{W}"));
        ability.addTarget(new TargetAttackingCreature());
        this.addAbility(ability);
    }

    private SwordDancer(final SwordDancer card) {
        super(card);
    }

    @Override
    public SwordDancer copy() {
        return new SwordDancer(this);
    }
}
