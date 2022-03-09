
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class SeedcradleWitch extends CardImpl {

    public SeedcradleWitch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G/W}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {2}{G}{W}: Target creature gets +3/+3 until end of turn. Untap that creature.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostTargetEffect(3, 3, Duration.EndOfTurn), new ManaCostsImpl("{2}{G}{W}"));
        ability.addEffect(new UntapTargetEffect().setText("untap that creature"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private SeedcradleWitch(final SeedcradleWitch card) {
        super(card);
    }

    @Override
    public SeedcradleWitch copy() {
        return new SeedcradleWitch(this);
    }
}
