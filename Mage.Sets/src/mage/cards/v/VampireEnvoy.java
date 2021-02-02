
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesTappedSourceTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class VampireEnvoy extends CardImpl {

    public VampireEnvoy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.CLERIC);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Vampire Envoy becomes tapped, you gain 1 life.
        this.addAbility(new BecomesTappedSourceTriggeredAbility(new GainLifeEffect(1)));
    }

    private VampireEnvoy(final VampireEnvoy card) {
        super(card);
    }

    @Override
    public VampireEnvoy copy() {
        return new VampireEnvoy(this);
    }
}
