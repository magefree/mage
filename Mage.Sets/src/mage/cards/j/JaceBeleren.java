
package mage.cards.j;

import java.util.UUID;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.common.DrawCardAllEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.PutLibraryIntoGraveTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.target.TargetPlayer;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class JaceBeleren extends CardImpl {

    public JaceBeleren(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.PLANESWALKER},"{1}{U}{U}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.JACE);

        this.setStartingLoyalty(3);

        // +2: Each player draws a card.
        this.addAbility(new LoyaltyAbility(new DrawCardAllEffect(1), 2));

        // -1: Target player draws a card.
        LoyaltyAbility ability1 = new LoyaltyAbility(new DrawCardTargetEffect(1), -1);
        ability1.addTarget(new TargetPlayer());
        this.addAbility(ability1);

        // -10: Target player puts the top twenty cards of their library into their graveyard.
        LoyaltyAbility ability2 = new LoyaltyAbility(new PutLibraryIntoGraveTargetEffect(20), -10);
        ability2.addTarget(new TargetPlayer());
        this.addAbility(ability2);
    }

    private JaceBeleren(final JaceBeleren card) {
        super(card);
    }

    @Override
    public JaceBeleren copy() {
        return new JaceBeleren(this);
    }

}
