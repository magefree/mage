
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.HuntedCentaurToken;
import mage.target.Target;
import mage.target.common.TargetOpponent;

/**
 *
 * @author Loki
 */
public final class HuntedHorror extends CardImpl {

    public HuntedHorror(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{B}");
        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        this.addAbility(TrampleAbility.getInstance());
        // When Hunted Horror enters the battlefield, create two 3/3 green Centaur creature tokens with protection from black under target opponent's control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new CreateTokenTargetEffect(new HuntedCentaurToken(), 2), false);
        Target target = new TargetOpponent();
        ability.addTarget(target);
        this.addAbility(ability);
    }

    private HuntedHorror(final HuntedHorror card) {
        super(card);
    }

    @Override
    public HuntedHorror copy() {
        return new HuntedHorror(this);
    }
}
