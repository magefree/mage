package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.constants.SubType;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class SiegebreakerGiant extends CardImpl {

    public SiegebreakerGiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");

        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(6);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // {3}{R}: Target creature can't block this turn.
        Ability ability = new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new CantBlockTargetEffect(Duration.EndOfTurn),
                new ManaCostsImpl<>("{3}{R}")
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private SiegebreakerGiant(final SiegebreakerGiant card) {
        super(card);
    }

    @Override
    public SiegebreakerGiant copy() {
        return new SiegebreakerGiant(this);
    }
}
