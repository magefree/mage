
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.AfflictAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author ciaccona007
 */
public final class FrontlineDevastator extends CardImpl {

    public FrontlineDevastator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.MINOTAUR);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Afflict 2
        addAbility(new AfflictAbility(2));
        // {1}{R}: Frontline Devastator gets +1/+0 until end of turn.
        addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1,0,Duration.EndOfTurn), new ManaCostsImpl<>("{1}{R}")));
    }

    private FrontlineDevastator(final FrontlineDevastator card) {
        super(card);
    }

    @Override
    public FrontlineDevastator copy() {
        return new FrontlineDevastator(this);
    }
}
