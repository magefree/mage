
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author Sir-Speshkitty
 */
public final class AbbeyMatron extends CardImpl {

    public AbbeyMatron(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {W}, {tap}: Abbey Matron gets +0/+3 until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(0,3,Duration.EndOfTurn), new ManaCostsImpl<>("{W}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private AbbeyMatron(final AbbeyMatron card) {
        super(card);
    }

    @Override
    public AbbeyMatron copy() {
        return new AbbeyMatron(this);
    }
}
