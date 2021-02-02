
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.abilities.keyword.CantBeBlockedSourceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.GoblinToken;
import mage.target.Target;
import mage.target.common.TargetOpponent;

/**
 *
 * @author Loki
 */
public final class HuntedPhantasm extends CardImpl {

    public HuntedPhantasm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}{U}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        // Hunted Phantasm can't be blocked.
        this.addAbility(new CantBeBlockedSourceAbility());
        // When Hunted Phantasm enters the battlefield, create five 1/1 red Goblin creature tokens under target opponent's control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new CreateTokenTargetEffect(new GoblinToken(), 5), false);
        Target target = new TargetOpponent();
        ability.addTarget(target);
        this.addAbility(ability);
    }

    private HuntedPhantasm(final HuntedPhantasm card) {
        super(card);
    }

    @Override
    public HuntedPhantasm copy() {
        return new HuntedPhantasm(this);
    }
}
