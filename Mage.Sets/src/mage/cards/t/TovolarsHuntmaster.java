package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.DayboundAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.WolfToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TovolarsHuntmaster extends CardImpl {

    public TovolarsHuntmaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);
        this.secondSideCardClazz = mage.cards.t.TovolarsPackleader.class;

        // Whenever Tovolar's Huntmaster enters the battlefield, create two 2/2 green Wolf creature tokens.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new WolfToken(), 2)));

        // Daybound
        this.addAbility(new DayboundAbility());
    }

    private TovolarsHuntmaster(final TovolarsHuntmaster card) {
        super(card);
    }

    @Override
    public TovolarsHuntmaster copy() {
        return new TovolarsHuntmaster(this);
    }
}
