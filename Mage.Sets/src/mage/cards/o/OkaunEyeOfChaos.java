
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.WinsCoinFlipTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.dynamicvalue.common.SourcePermanentToughnessValue;
import mage.abilities.effects.common.FlipUntilLoseEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.PartnerWithAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;

/**
 *
 * @author TheElk801
 */
public final class OkaunEyeOfChaos extends CardImpl {

    public OkaunEyeOfChaos(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.CYCLOPS);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Partner with Zndrsplt, Eye of Wisdom (When this creature enters the battlefield, target player may put Zndrsplt into their hand from their library, then shuffle.)
        this.addAbility(new PartnerWithAbility("Zndrsplt, Eye of Wisdom", true));

        // At the beginning of combat on your turn, flip a coin until you lose a flip.
        this.addAbility(new BeginningOfCombatTriggeredAbility(new FlipUntilLoseEffect(), TargetController.YOU, false));

        // Whenever a player wins a coin flip, double Okaun's power and toughness until end of turn.
        DynamicValue sourcePower = new SourcePermanentPowerCount();
        DynamicValue sourceToughness = new SourcePermanentToughnessValue();
        this.addAbility(new WinsCoinFlipTriggeredAbility(
                new BoostSourceEffect(
                        sourcePower,
                        sourceToughness,
                        Duration.EndOfTurn,
                        true
                ).setText("double {this}'s power and toughness until end of turn")
        ));
    }

    public OkaunEyeOfChaos(final OkaunEyeOfChaos card) {
        super(card);
    }

    @Override
    public OkaunEyeOfChaos copy() {
        return new OkaunEyeOfChaos(this);
    }
}
