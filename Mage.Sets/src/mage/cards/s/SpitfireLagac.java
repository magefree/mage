package mage.cards.s;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;

/**
 * @author TheElk801
 */
public final class SpitfireLagac extends CardImpl {

    public SpitfireLagac(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.LIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Landfall — Whenever a land enters the battlefield under your control, Spitfire Lagac deals 1 damage to each opponent.
        this.addAbility(new LandfallAbility(new DamagePlayersEffect(1, TargetController.OPPONENT), false));
    }

    private SpitfireLagac(final SpitfireLagac card) {
        super(card);
    }

    @Override
    public SpitfireLagac copy() {
        return new SpitfireLagac(this);
    }
}
