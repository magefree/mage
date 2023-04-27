
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FearAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author cbt33
 */
public final class FaceOfFear extends CardImpl {

    public FaceOfFear(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{B}");
        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // {2}{B}, Discard a card: Face of Fear gains fear until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilitySourceEffect(FearAbility.getInstance(), Duration.EndOfTurn), new ManaCostsImpl<>("{2}{B}"));
        ability.addCost(new DiscardCardCost(false));
        this.addAbility(ability);
    }

    private FaceOfFear(final FaceOfFear card) {
        super(card);
    }

    @Override
    public FaceOfFear copy() {
        return new FaceOfFear(this);
    }
}
