
package mage.cards.a;

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
import mage.target.common.TargetCardInHand;

/**
 *
 * @author LevelX2
 */
public final class AuntiesHovel extends CardImpl {

    private static final FilterCard filter = new FilterCard("a Goblin card from your hand");
    static {
        filter.add(SubType.GOBLIN.getPredicate());
    }

    public AuntiesHovel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // As Auntie's Hovel enters the battlefield, you may reveal a Goblin card from your hand. If you don't, Auntie's Hovel enters the battlefield tapped.
        this.addAbility(new AsEntersBattlefieldAbility(new TapSourceUnlessPaysEffect(new RevealTargetFromHandCost(new TargetCardInHand(filter))), "you may reveal a Goblin card from your hand. If you don't, {this} enters the battlefield tapped"));
        // {tap}: Add {B} or {R}.
        this.addAbility(new BlackManaAbility());
        this.addAbility(new RedManaAbility());
    }

    private AuntiesHovel(final AuntiesHovel card) {
        super(card);
    }

    @Override
    public AuntiesHovel copy() {
        return new AuntiesHovel(this);
    }
}
