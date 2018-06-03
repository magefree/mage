
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author LoneFox

 */
public final class Drekavac extends CardImpl {

    private static final FilterCard filter = new FilterCard("noncreature card");

    static {
        filter.add(Predicates.not(new CardTypePredicate(CardType.CREATURE)));
    }

    public Drekavac(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Drekavac enters the battlefield, sacrifice it unless you discard a noncreature card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SacrificeSourceUnlessPaysEffect(new DiscardTargetCost(new TargetCardInHand(filter)))));
    }

    public Drekavac(final Drekavac card) {
        super(card);
    }

    @Override
    public Drekavac copy() {
        return new Drekavac(this);
    }
}
