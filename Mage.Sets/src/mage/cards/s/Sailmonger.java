
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author L_J
 */
public final class Sailmonger extends CardImpl {

    public Sailmonger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MONGER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {2}: Target creature gains flying until end of turn. Any player may activate this ability.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilityTargetEffect(FlyingAbility.getInstance(), Duration.EndOfTurn), new ManaCostsImpl<>("{2}"));
        ability.addTarget(new TargetCreaturePermanent());
        ability.setMayActivate(TargetController.ANY);
        ability.addEffect(new InfoEffect("Any player may activate this ability"));
        this.addAbility(ability);
    }

    private Sailmonger(final Sailmonger card) {
        super(card);
    }

    @Override
    public Sailmonger copy() {
        return new Sailmonger(this);
    }
}
