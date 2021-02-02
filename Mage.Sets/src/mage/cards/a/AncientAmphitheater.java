
package mage.cards.a;

import java.util.UUID;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.costs.common.RevealTargetFromHandCost;
import mage.abilities.effects.common.TapSourceUnlessPaysEffect;
import mage.abilities.mana.RedManaAbility;
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
public final class AncientAmphitheater extends CardImpl {

    private static final FilterCard filter = new FilterCard("a Giant from your hand");
    static {
        filter.add(SubType.GIANT.getPredicate());
    }

    public AncientAmphitheater(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // As Ancient Amphitheater enters the battlefield, you may reveal a Giant card from your hand. If you don't, Ancient Amphitheater enters the battlefield tapped.
        this.addAbility(new AsEntersBattlefieldAbility(new TapSourceUnlessPaysEffect(new RevealTargetFromHandCost(new TargetCardInHand(filter))), "you may reveal a Giant card from your hand. If you don't, {this} enters the battlefield tapped"));
        // {tap}: Add {R} or {W}.
        this.addAbility(new RedManaAbility());
        this.addAbility(new WhiteManaAbility());

        
    }

    private AncientAmphitheater(final AncientAmphitheater card) {
        super(card);
    }

    @Override
    public AncientAmphitheater copy() {
        return new AncientAmphitheater(this);
    }
}
