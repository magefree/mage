package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.YoureDealtDamageTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.SoldierToken;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class DarienKingOfKjeldor extends CardImpl {

    public DarienKingOfKjeldor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever you're dealt damage, you may create that many 1/1 white Soldier creature tokens.
        this.addAbility(new YoureDealtDamageTriggeredAbility(new CreateTokenEffect(
                new SoldierToken(), SavedDamageValue.MANY), true));
    }

    private DarienKingOfKjeldor(final DarienKingOfKjeldor card) {
        super(card);
    }

    @Override
    public DarienKingOfKjeldor copy() {
        return new DarienKingOfKjeldor(this);
    }
}
