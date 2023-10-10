
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author cbt33
 */
public final class SliverOverlord extends CardImpl {
    
    private static final FilterCard filter = new FilterCard("Sliver card");
    
    static{
        filter.add(SubType.SLIVER.getPredicate());
    }

    public SliverOverlord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}{U}{B}{R}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SLIVER);
        this.subtype.add(SubType.MUTANT);

        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // {3}: Search your library for a Sliver card, reveal that card, and put it into your hand. Then shuffle your library.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true, true), new ManaCostsImpl<>("{3}")));
        
        // {3}: Gain control of target Sliver.
        Ability ability = (new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainControlTargetEffect(Duration.Custom), new ManaCostsImpl<>("{3}")));
        Target target = new TargetPermanent(new FilterCreaturePermanent(SubType.SLIVER,"Sliver"));
        ability.addTarget(target);
        this.addAbility(ability);
    }

    private SliverOverlord(final SliverOverlord card) {
        super(card);
    }

    @Override
    public SliverOverlord copy() {
        return new SliverOverlord(this);
    }
}
