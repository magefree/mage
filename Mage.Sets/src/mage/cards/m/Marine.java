package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author NinthWorld
 */
public final class Marine extends CardImpl {

    public Marine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");
        
        this.subtype.add(SubType.TERRAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {R}: Marine gets +2/-1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(new BoostSourceEffect(2, -1, Duration.EndOfTurn), new ManaCostsImpl("{R}")));
    }

    public Marine(final Marine card) {
        super(card);
    }

    @Override
    public Marine copy() {
        return new Marine(this);
    }
}
