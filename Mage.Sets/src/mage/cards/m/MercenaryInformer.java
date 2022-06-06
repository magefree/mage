
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CantBeTargetedSourceEffect;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterObject;
import mage.filter.FilterPermanent;
import mage.filter.FilterStackObject;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.target.TargetPermanent;

/**
 *
 * @author fireshoes
 */
public final class MercenaryInformer extends CardImpl {
    
    private static final FilterObject filterBlack = new FilterStackObject("black spells or abilities from black sources");
    private static final FilterPermanent filterMercenary = new FilterPermanent("nontoken Mercenary");

    static {
        filterBlack.add(new ColorPredicate(ObjectColor.BLACK));
        filterMercenary.add(TokenPredicate.FALSE);
        filterMercenary.add(SubType.MERCENARY.getPredicate());
    }

    public MercenaryInformer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.REBEL);
        this.subtype.add(SubType.MERCENARY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Mercenary Informer can't be the target of black spells or abilities from black sources.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantBeTargetedSourceEffect(filterBlack, Duration.WhileOnBattlefield)));
        
        // {2}{W}: Put target nontoken Mercenary on the bottom of its owner's library.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PutOnLibraryTargetEffect(false), new ManaCostsImpl<>("{2}{W}"));
        ability.addTarget(new TargetPermanent(filterMercenary));
        this.addAbility(ability);
    }

    private MercenaryInformer(final MercenaryInformer card) {
        super(card);
    }

    @Override
    public MercenaryInformer copy() {
        return new MercenaryInformer(this);
    }
}
