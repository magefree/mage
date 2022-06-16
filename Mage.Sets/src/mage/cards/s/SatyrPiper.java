
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.MustBeBlockedByAtLeastOneTargetEffect;
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
public final class SatyrPiper extends CardImpl {

    public SatyrPiper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.SATYR);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {3}{G}: Target creature must be blocked this turn if able.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new MustBeBlockedByAtLeastOneTargetEffect(Duration.EndOfTurn), new ManaCostsImpl<>("{3}{G}"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

    }

    private SatyrPiper(final SatyrPiper card) {
        super(card);
    }

    @Override
    public SatyrPiper copy() {
        return new SatyrPiper(this);
    }
}
