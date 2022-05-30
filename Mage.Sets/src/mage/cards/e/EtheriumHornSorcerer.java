
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.keyword.CascadeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class EtheriumHornSorcerer extends CardImpl {

    public EtheriumHornSorcerer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{4}{U}{R}");
        this.subtype.add(SubType.MINOTAUR);
        this.subtype.add(SubType.WIZARD);


        this.power = new MageInt(3);
        this.toughness = new MageInt(6);

        // {1}{U}{R}: Return Etherium-Horn Sorcerer to its owner's hand.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new ReturnToHandSourceEffect(true), new ManaCostsImpl<>("{1}{U}{R}")));

        // Cascade
        this.addAbility(new CascadeAbility());
    }

    private EtheriumHornSorcerer(final EtheriumHornSorcerer card) {
        super(card);
    }

    @Override
    public EtheriumHornSorcerer copy() {
        return new EtheriumHornSorcerer(this);
    }
}
