

package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.BattleCryAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.SoldierToken;

/**
 * @author Loki
 */
public final class HeroOfBladehold extends CardImpl {

    public HeroOfBladehold(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Battle cry (Whenever this creature attacks, each other attacking creature gets +1/+0 until end of turn.)
        this.addAbility(new BattleCryAbility());

        // Whenever Hero of Bladehold attacks, create two 1/1 white Soldier creature tokens tapped and attacking.
        this.addAbility(new AttacksTriggeredAbility(new CreateTokenEffect(new SoldierToken(), 2, true, true), false));

    }

    private HeroOfBladehold(final HeroOfBladehold card) {
        super(card);
    }

    @Override
    public HeroOfBladehold copy() {
        return new HeroOfBladehold(this);
    }

}
