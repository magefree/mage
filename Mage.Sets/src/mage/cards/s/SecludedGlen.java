
package mage.cards.s;

import java.util.UUID;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.costs.common.RevealTargetFromHandCost;
import mage.abilities.effects.common.TapSourceUnlessPaysEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.BlueManaAbility;
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
public final class SecludedGlen extends CardImpl {

    private static final FilterCard filter = new FilterCard("a Faerie from your hand");
    static {
        filter.add(SubType.FAERIE.getPredicate());
    }

    public SecludedGlen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // As Secluded Glen enters the battlefield, you may reveal a Faerie card from your hand. If you don't, Secluded Glen enters the battlefield tapped.
        this.addAbility(new AsEntersBattlefieldAbility(new TapSourceUnlessPaysEffect(new RevealTargetFromHandCost(new TargetCardInHand(filter))), "you may reveal a Faerie card from your hand. If you don't, {this} enters the battlefield tapped"));
        // {tap}: Add {U} or {B}.
        this.addAbility(new BlueManaAbility());
        this.addAbility(new BlackManaAbility());
    }

    private SecludedGlen(final SecludedGlen card) {
        super(card);
    }

    @Override
    public SecludedGlen copy() {
        return new SecludedGlen(this);
    }
}
