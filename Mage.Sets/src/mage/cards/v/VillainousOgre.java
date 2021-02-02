
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.decorator.ConditionalGainActivatedAbility;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;

/**
 *
 * @author LevelX
 */
public final class VillainousOgre extends CardImpl {

    private static final String rule = "As long as you control a Demon, {this} has {B}: Regenerate Villainous Ogre";
    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Demon");
    static {
        filter.add(SubType.DEMON.getPredicate());
    }

    public VillainousOgre(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        this.addAbility(new CantBlockAbility());

        // As long as you control a Demon, Villainous Ogre has "{B}: Regenerate Villainous Ogre.
        this.addAbility(new ConditionalGainActivatedAbility(
                Zone.BATTLEFIELD,
                new RegenerateSourceEffect(), 
                new ColoredManaCost(ColoredManaSymbol.B),
                new PermanentsOnTheBattlefieldCondition(filter), 
                rule));        
    }

    private VillainousOgre(final VillainousOgre card) {
        super(card);
    }

    @Override
    public VillainousOgre copy() {
        return new VillainousOgre(this);
    }    

}
