
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ColoredManaSymbol;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;

/**
 *
 * @author emerald000
 */
public final class GrimHaruspex extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another nontoken creature you control");
    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(AnotherPredicate.instance);
        filter.add(TokenPredicate.FALSE);
    }

    public GrimHaruspex(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Morph {B}
        this.addAbility(new MorphAbility(new ColoredManaCost(ColoredManaSymbol.B)));
        
        // Whenever another nontoken creature you control dies, draw a card.
        this.addAbility(new DiesCreatureTriggeredAbility(new DrawCardSourceControllerEffect(1), false, filter));
    }

    private GrimHaruspex(final GrimHaruspex card) {
        super(card);
    }

    @Override
    public GrimHaruspex copy() {
        return new GrimHaruspex(this);
    }
}
