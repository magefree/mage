package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.CatToken2;

/**
 *
 * @author TheElk801
 */
public final class LeoninWarleader extends CardImpl {

    public LeoninWarleader(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");

        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever Leonin Warleader attacks, create two 1/1 white Cat creature tokens with lifelink that are tapped and attacking.
        this.addAbility(new AttacksTriggeredAbility(
                new CreateTokenEffect(new CatToken2(), 2, true, true), false
        ));
    }

    private LeoninWarleader(final LeoninWarleader card) {
        super(card);
    }

    @Override
    public LeoninWarleader copy() {
        return new LeoninWarleader(this);
    }
}
