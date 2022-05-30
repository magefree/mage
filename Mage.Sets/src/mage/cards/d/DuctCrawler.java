
package mage.cards.d;

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
 * @author anonymous
 */
public final class DuctCrawler extends CardImpl {

    public DuctCrawler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}");
        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}{R}: Target creature can't block Duct Crawler this turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CantBeBlockedByTargetSourceEffect(Duration.EndOfTurn), 
        		new ManaCostsImpl<>("{1}{R}"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private DuctCrawler(final DuctCrawler card) {
        super(card);
    }

    @Override
    public DuctCrawler copy() {
        return new DuctCrawler(this);
    }
}
