
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.LookAtTargetPlayerHandEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPlayer;

/**
 *
 * @author fireshoes
 */
public final class IngeniousThief extends CardImpl {

    public IngeniousThief(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Ingenious Thief enters the battlefield, look at target player's hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new LookAtTargetPlayerHandEffect(), false);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private IngeniousThief(final IngeniousThief card) {
        super(card);
    }

    @Override
    public IngeniousThief copy() {
        return new IngeniousThief(this);
    }
}
