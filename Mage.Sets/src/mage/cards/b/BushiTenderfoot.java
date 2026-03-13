package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.DealtDamageAndDiedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.FlipSourceEffect;
import mage.abilities.keyword.BushidoAbility;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.custom.CreatureToken;

import java.util.UUID;

/**
 * @author LevelX
 */
public final class BushiTenderfoot extends CardImpl {

    public BushiTenderfoot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");
        this.subtype.add(SubType.HUMAN, SubType.SOLDIER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.flipCard = true;
        this.flipCardName = "Kenzo the Hardhearted";

        CreatureToken flipToken = new CreatureToken(3, 4, "", SubType.HUMAN, SubType.SAMURAI)
            .withName("Kenzo the Hardhearted")
            .withSuperType(SuperType.LEGENDARY)
            .withColor("W")
            .withAbility(DoubleStrikeAbility.getInstance())
            .withAbility(new BushidoAbility(2));

        // When a creature dealt damage by Bushi Tenderfoot this turn dies, flip Bushi Tenderfoot.
        Effect effect = new FlipSourceEffect(flipToken);
        effect.setText("flip {this}");
        this.addAbility(new DealtDamageAndDiedTriggeredAbility(effect));
    }

    private BushiTenderfoot(final BushiTenderfoot card) {
        super(card);
    }

    @Override
    public BushiTenderfoot copy() {
        return new BushiTenderfoot(this);
    }
}
