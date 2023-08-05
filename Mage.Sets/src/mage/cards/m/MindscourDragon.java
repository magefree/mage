
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToOpponentTriggeredAbility;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPlayer;

/**
 *
 * @author emerald000
 */
public final class MindscourDragon extends CardImpl {

    public MindscourDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}{U}");
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Mindscour Dragon deals combat damage to an opponent, target player puts the top four cards of their library into their graveyard.
        Ability ability = new DealsDamageToOpponentTriggeredAbility(new MillCardsTargetEffect(4), false, true);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private MindscourDragon(final MindscourDragon card) {
        super(card);
    }

    @Override
    public MindscourDragon copy() {
        return new MindscourDragon(this);
    }
}
