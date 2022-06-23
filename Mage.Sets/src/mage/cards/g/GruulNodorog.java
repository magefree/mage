
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.MenaceAbility;
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
public final class GruulNodorog extends CardImpl {

    public GruulNodorog(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}{G}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        //{R}: Gruul Nodorog gains menace until end of turn. (It can't be blocked except by two or more creatures.)
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilitySourceEffect(new MenaceAbility(), Duration.EndOfTurn), new ManaCostsImpl<>("{R}")));
    }

    private GruulNodorog(final GruulNodorog card) {
        super(card);
    }

    @Override
    public GruulNodorog copy() {
        return new GruulNodorog(this);
    }
}