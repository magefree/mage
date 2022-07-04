
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.HasteAbility;
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
public final class SavageKnuckleblade extends CardImpl {

    public SavageKnuckleblade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}{U}{R}");
        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // {2}{G}: Savage Knuckleblade gets +2/+2 until end of turn.  Activate this ability only once each turn.
        this.addAbility(new LimitedTimesPerTurnActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(2, 2, Duration.EndOfTurn), new ManaCostsImpl<>("{2}{G}")));

        // {2}{U}: Return Savage Knuckleblade to its owner's hand.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new ReturnToHandSourceEffect(true), new ManaCostsImpl<>("{2}{U}")));
        
        // {R}: Savage Knuckleblade gains haste until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilitySourceEffect(HasteAbility.getInstance(), Duration.EndOfTurn), new ManaCostsImpl<>("{R}")));


    }

    private SavageKnuckleblade(final SavageKnuckleblade card) {
        super(card);
    }

    @Override
    public SavageKnuckleblade copy() {
        return new SavageKnuckleblade(this);
    }
}
