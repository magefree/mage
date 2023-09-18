
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
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
public final class RebelInformer extends CardImpl {
    
    private static final FilterObject filterWhite = new FilterStackObject("white spells or abilities from white sources");
    private static final FilterPermanent filterRebel = new FilterPermanent("nontoken Rebel");

    static {
        filterWhite.add(new ColorPredicate(ObjectColor.WHITE));
        filterRebel.add(TokenPredicate.FALSE);
        filterRebel.add(SubType.REBEL.getPredicate());
    }

    public RebelInformer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MERCENARY);
        this.subtype.add(SubType.REBEL);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Rebel Informer can't be the target of white spells or abilities from white sources.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantBeTargetedSourceEffect(filterWhite, Duration.WhileOnBattlefield)));
        
        // {3}: Put target nontoken Rebel on the bottom of its owner's library.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PutOnLibraryTargetEffect(false), new GenericManaCost(3));
        ability.addTarget(new TargetPermanent(filterRebel));
        this.addAbility(ability);
    }

    private RebelInformer(final RebelInformer card) {
        super(card);
    }

    @Override
    public RebelInformer copy() {
        return new RebelInformer(this);
    }
}
