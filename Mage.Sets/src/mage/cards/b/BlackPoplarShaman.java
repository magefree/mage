
package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.RegenerateTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 *
 * @author Loki
 */
public final class BlackPoplarShaman extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Treefolk");

    static {
        filter.add(SubType.TREEFOLK.getPredicate());
    }

    public BlackPoplarShaman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.TREEFOLK, SubType.SHAMAN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateTargetEffect(), new ManaCostsImpl<>("{2}{B}"));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private BlackPoplarShaman(final BlackPoplarShaman card) {
        super(card);
    }

    @Override
    public BlackPoplarShaman copy() {
        return new BlackPoplarShaman(this);
    }
}
