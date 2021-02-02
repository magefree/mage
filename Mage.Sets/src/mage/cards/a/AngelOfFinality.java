
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ExileGraveyardAllTargetPlayerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public final class AngelOfFinality extends CardImpl {

    public AngelOfFinality(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.ANGEL);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // When Angel of Finality enters the battlefield, exile all cards from target player's graveyard.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileGraveyardAllTargetPlayerEffect());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private AngelOfFinality(final AngelOfFinality card) {
        super(card);
    }

    @Override
    public AngelOfFinality copy() {
        return new AngelOfFinality(this);
    }
}
