
package mage.cards.r;

import java.util.UUID;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.costs.common.RevealTargetFromHandCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.TapSourceUnlessPaysEffect;
import mage.abilities.keyword.ReinforceAbility;
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
public final class RusticClachan extends CardImpl {

    private static final FilterCard filter = new FilterCard("a Kithkin card from your hand");

    static {
        filter.add(SubType.KITHKIN.getPredicate());
    }

    public RusticClachan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // As Rustic Clachan enters the battlefield, you may reveal a Kithkin card from your hand. If you don't, Rustic Clachan enters the battlefield tapped.
        this.addAbility(new AsEntersBattlefieldAbility(new TapSourceUnlessPaysEffect(new RevealTargetFromHandCost(new TargetCardInHand(filter))), "you may reveal a Kithkin card from your hand. If you don't, {this} enters the battlefield tapped"));

        // {tap}: Add {W}.
        this.addAbility(new WhiteManaAbility());
        // Reinforce 1-{1}{W}
        this.addAbility(new ReinforceAbility(1, new ManaCostsImpl<>("{1}{W}")));
    }

    private RusticClachan(final RusticClachan card) {
        super(card);
    }

    @Override
    public RusticClachan copy() {
        return new RusticClachan(this);
    }
}
