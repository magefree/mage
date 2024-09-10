package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.AttacksWhileSaddledTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.SaddleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.Angel33Token;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class SeraphicSteed extends CardImpl {

    public SeraphicSteed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{W}");

        this.subtype.add(SubType.UNICORN);
        this.subtype.add(SubType.MOUNT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Whenever Seraphic Steed attacks while saddled, create a 3/3 white Angel creature token with flying.
        this.addAbility(new AttacksWhileSaddledTriggeredAbility(new CreateTokenEffect(new Angel33Token())));
        
        // Saddle 4
        this.addAbility(new SaddleAbility(4));

    }

    private SeraphicSteed(final SeraphicSteed card) {
        super(card);
    }

    @Override
    public SeraphicSteed copy() {
        return new SeraphicSteed(this);
    }
}
