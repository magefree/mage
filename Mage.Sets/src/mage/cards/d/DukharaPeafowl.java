
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class DukharaPeafowl extends CardImpl {

    public DukharaPeafowl(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{4}");
        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // {U}: Dukhara Peafowl gains flying until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.EndOfTurn), new ManaCostsImpl<>("{U}")));

    }

    private DukharaPeafowl(final DukharaPeafowl card) {
        super(card);
    }

    @Override
    public DukharaPeafowl copy() {
        return new DukharaPeafowl(this);
    }
}
