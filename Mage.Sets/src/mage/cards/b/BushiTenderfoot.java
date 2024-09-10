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
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.WasDealtDamageThisTurnPredicate;
import mage.game.permanent.token.TokenImpl;

import java.util.UUID;

/**
 * @author LevelX
 */
public final class BushiTenderfoot extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature that was dealt damage this turn");

    static {
        filter.add(WasDealtDamageThisTurnPredicate.instance);
    }

    public BushiTenderfoot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");
        this.subtype.add(SubType.HUMAN, SubType.SOLDIER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.flipCard = true;
        this.flipCardName = "Kenzo the Hardhearted";

        // When a creature dealt damage by Bushi Tenderfoot this turn dies, flip Bushi Tenderfoot.
        Effect effect = new FlipSourceEffect(new KenzoTheHardhearted());
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

class KenzoTheHardhearted extends TokenImpl {

    KenzoTheHardhearted() {
        super("Kenzo the Hardhearted", "");
        this.supertype.add(SuperType.LEGENDARY);
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.HUMAN, SubType.SAMURAI);
        power = new MageInt(3);
        toughness = new MageInt(4);

        // Double strike; bushido 2 (When this blocks or becomes blocked, it gets +2/+2 until end of turn.)
        this.addAbility(DoubleStrikeAbility.getInstance());
        this.addAbility(new BushidoAbility(2));
    }
    private KenzoTheHardhearted(final KenzoTheHardhearted token) {
        super(token);
    }

    public KenzoTheHardhearted copy() {
        return new KenzoTheHardhearted(this);
    }
}
