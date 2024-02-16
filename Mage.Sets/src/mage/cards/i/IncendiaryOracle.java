package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.replacement.DealtDamageToCreatureBySourceDies;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.watchers.common.DamagedByWatcher;

/**
 *
 * @author LevelX2
 */
public final class IncendiaryOracle extends CardImpl {

    public IncendiaryOracle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {1}{R}: Incendiary Oracle gets +1/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new BoostSourceEffect(1, 0, Duration.EndOfTurn), new ManaCostsImpl<>("{1}{R}")));

        // If a creature dealt damage by Incendiary Oracle this turn would die, exile it instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new DealtDamageToCreatureBySourceDies(this, Duration.WhileOnBattlefield)), new DamagedByWatcher(false));
    }

    private IncendiaryOracle(final IncendiaryOracle card) {
        super(card);
    }

    @Override
    public IncendiaryOracle copy() {
        return new IncendiaryOracle(this);
    }
}
