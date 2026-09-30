package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.KlingonWarriorToken;

/**
 *
 * @author muz
 */
public final class ChaDIchInvestigator extends CardImpl {

    public ChaDIchInvestigator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.subtype.add(SubType.KLINGON);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When this creature enters, create a 2/1 red Klingon Warrior creature token with haste.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new KlingonWarriorToken())));
    }

    private ChaDIchInvestigator(final ChaDIchInvestigator card) {
        super(card);
    }

    @Override
    public ChaDIchInvestigator copy() {
        return new ChaDIchInvestigator(this);
    }
}
