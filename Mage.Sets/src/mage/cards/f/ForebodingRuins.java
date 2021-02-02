
package mage.cards.f;

import java.util.UUID;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.costs.common.RevealTargetFromHandCost;
import mage.abilities.effects.common.TapSourceUnlessPaysEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author fireshoes
 */
public final class ForebodingRuins extends CardImpl {

    private static final FilterCard filter = new FilterCard("a Swamp or Mountain card from your hand");

    static {
        filter.add(Predicates.or(SubType.SWAMP.getPredicate(),
                SubType.MOUNTAIN.getPredicate()));
    }

    public ForebodingRuins(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // As Foreboding Ruins enters the battlefield, you may reveal a Swamp or Mountain card from your hand. If you don't Foreboding Ruins enters the battlefield tapped.
        this.addAbility(new AsEntersBattlefieldAbility(new TapSourceUnlessPaysEffect(new RevealTargetFromHandCost(new TargetCardInHand(filter))),
                "you may reveal a Swamp or Mountain card from your hand. If you don't, {this} enters the battlefield tapped"));

        // {T}: Add {B} or {R}.
        this.addAbility(new BlackManaAbility());
        this.addAbility(new RedManaAbility());
    }

    private ForebodingRuins(final ForebodingRuins card) {
        super(card);
    }

    @Override
    public ForebodingRuins copy() {
        return new ForebodingRuins(this);
    }
}
