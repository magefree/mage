package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.EldraziScionToken;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class Blisterpod extends CardImpl {

    public Blisterpod(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");
        this.subtype.add(SubType.ELDRAZI, SubType.DRONE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // When Blisterpod dies, create a 1/1 colorless Eldrazi Scion creature token. It has "Sacrifice this creature: Add {C}."
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new EldraziScionToken())
                .setText("create a 1/1 colorless Eldrazi Scion creature token. It has \"Sacrifice this creature: Add {C}.\""), false));
    }

    private Blisterpod(final Blisterpod card) {
        super(card);
    }

    @Override
    public Blisterpod copy() {
        return new Blisterpod(this);
    }
}
