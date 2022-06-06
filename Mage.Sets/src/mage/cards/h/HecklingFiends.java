
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.AttacksIfAbleTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class HecklingFiends extends CardImpl {

    public HecklingFiends(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.DEVIL);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {2}{R}: Target creature attacks this turn if able.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new AttacksIfAbleTargetEffect(Duration.EndOfTurn),
                new ManaCostsImpl<>("{2}{R}"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private HecklingFiends(final HecklingFiends card) {
        super(card);
    }

    @Override
    public HecklingFiends copy() {
        return new HecklingFiends(this);
    }
}
