
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.DrawDiscardOneOfThemEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author emerald000
 */
public final class KrovikanSorcerer extends CardImpl {
    
    private static final FilterCard filterNonBlack = new FilterCard("a nonblack card");
    private static final FilterCard filterBlack = new FilterCard("a black card");
    static {
        filterNonBlack.add(Predicates.not(new ColorPredicate(ObjectColor.BLACK)));
        filterBlack.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public KrovikanSorcerer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}, Discard a nonblack card: Draw a card.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), new TapSourceCost());
        ability.addCost(new DiscardTargetCost(new TargetCardInHand(filterNonBlack)));
        this.addAbility(ability);
        
        // {tap}, Discard a black card: Draw two cards, then discard one of them.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawDiscardOneOfThemEffect(2), new TapSourceCost());
        ability.addCost(new DiscardTargetCost(new TargetCardInHand(filterBlack)));
        this.addAbility(ability);
    }

    private KrovikanSorcerer(final KrovikanSorcerer card) {
        super(card);
    }

    @Override
    public KrovikanSorcerer copy() {
        return new KrovikanSorcerer(this);
    }
}