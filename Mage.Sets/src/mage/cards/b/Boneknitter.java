
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.RegenerateTargetEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.target.TargetPermanent;

/**
 *
 * @author LoneFox
 */
public final class Boneknitter extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Zombie");

    static {
        filter.add(SubType.ZOMBIE.getPredicate());
    }

    public Boneknitter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}{B}: Regenerate target Zombie.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateTargetEffect(), new ManaCostsImpl("{1}{B}"));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
        // Morph {2}{B}
        this.addAbility(new MorphAbility(new ManaCostsImpl("{2}{B}")));
    }

    private Boneknitter(final Boneknitter card) {
        super(card);
    }

    @Override
    public Boneknitter copy() {
        return new Boneknitter(this);
    }
}
