
package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.UnearthAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author Loki
 */
public final class UndeadLeotau extends CardImpl {

    public UndeadLeotau(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.CAT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // {R}: Undead Leotau gets +1/-1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(+1, -1, Duration.EndOfTurn), new ManaCostsImpl<>("{R}")));

        // Unearth {2}{B}
        this.addAbility(new UnearthAbility(new ManaCostsImpl<>("{2}{B}")));
    }

    private UndeadLeotau(final UndeadLeotau card) {
        super(card);
    }

    @Override
    public UndeadLeotau copy() {
        return new UndeadLeotau(this);
    }
}
