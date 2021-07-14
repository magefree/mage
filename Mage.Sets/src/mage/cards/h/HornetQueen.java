package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.InsectDeathToken;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class HornetQueen extends CardImpl {

    public HornetQueen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}{G}");
        this.subtype.add(SubType.INSECT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());
        // When Hornet Queen enters the battlefield, create four 1/1 green Insect creature tokens with flying and deathtouch.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new InsectDeathToken(), 4), false));
    }

    private HornetQueen(final HornetQueen card) {
        super(card);
    }

    @Override
    public HornetQueen copy() {
        return new HornetQueen(this);
    }
}
