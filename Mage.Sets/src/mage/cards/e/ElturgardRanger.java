package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.WolfToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ElturgardRanger extends CardImpl {

    public ElturgardRanger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.RANGER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(1);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // When Elturgard Ranger enters the battlefield, create a 2/2 green Wolf creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new WolfToken())));
    }

    private ElturgardRanger(final ElturgardRanger card) {
        super(card);
    }

    @Override
    public ElturgardRanger copy() {
        return new ElturgardRanger(this);
    }
}
