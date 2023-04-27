

package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class Gigantomancer extends CardImpl {

    public Gigantomancer (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{7}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        
        //{1}: Target creature you control has base power and toughness 7/7 until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new SetBasePowerToughnessTargetEffect(7, 7, Duration.EndOfTurn), new GenericManaCost(1));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    public Gigantomancer (final Gigantomancer card) {
        super(card);
    }

    @Override
    public Gigantomancer copy() {
        return new Gigantomancer(this);
    }

}
