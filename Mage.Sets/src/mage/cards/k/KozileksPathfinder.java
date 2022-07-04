
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.CantBeBlockedByTargetSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class KozileksPathfinder extends CardImpl {

    public KozileksPathfinder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{6}");
        this.subtype.add(SubType.ELDRAZI);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // {C}: Target creature can't block Kozilek's Pathfinder this turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CantBeBlockedByTargetSourceEffect(Duration.EndOfTurn),
                new ManaCostsImpl<>("{C}"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private KozileksPathfinder(final KozileksPathfinder card) {
        super(card);
    }

    @Override
    public KozileksPathfinder copy() {
        return new KozileksPathfinder(this);
    }
}
