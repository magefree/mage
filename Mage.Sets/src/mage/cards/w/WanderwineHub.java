
package mage.cards.w;

import java.util.UUID;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.costs.common.RevealTargetFromHandCost;
import mage.abilities.effects.common.TapSourceUnlessPaysEffect;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.WhiteManaAbility;
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
public final class WanderwineHub extends CardImpl {

    private static final FilterCard filter = new FilterCard("a Merfolk card from your hand");
    static {
        filter.add(SubType.MERFOLK.getPredicate());
    }

    public WanderwineHub(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // As Wanderwine Hub enters the battlefield, you may reveal a Merfolk card from your hand. If you don't, Wanderwine Hub enters the battlefield tapped.
        this.addAbility(new AsEntersBattlefieldAbility(new TapSourceUnlessPaysEffect(new RevealTargetFromHandCost(new TargetCardInHand(filter))), "you may reveal a Merfolk card from your hand. If you don't, {this} enters the battlefield tapped"));
        // {tap}: Add {W} or {U}.
        this.addAbility(new WhiteManaAbility());
        this.addAbility(new BlueManaAbility());

    }

    private WanderwineHub(final WanderwineHub card) {
        super(card);
    }

    @Override
    public WanderwineHub copy() {
        return new WanderwineHub(this);
    }
}
