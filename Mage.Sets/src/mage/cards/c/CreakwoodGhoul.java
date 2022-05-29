
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetCardInGraveyard;


/**
 *
 * @author Loki
 */
public final class CreakwoodGhoul extends CardImpl {

    public CreakwoodGhoul(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}");
        this.subtype.add(SubType.PLANT);
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ExileTargetEffect(), new ManaCostsImpl<>("{B/G}{B/G}")) ;
        ability.addTarget(new TargetCardInGraveyard());
        ability.addEffect(new GainLifeEffect(1));
        this.addAbility(ability);
    }

    private CreakwoodGhoul(final CreakwoodGhoul card) {
        super(card);
    }

    @Override
    public CreakwoodGhoul copy() {
        return new CreakwoodGhoul(this);
    }
}
