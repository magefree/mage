package mage.cards.n;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.condition.common.ControlArtifactAndEnchantmentCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.common.ControlArtifactAndEnchantmentHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.SamuraiToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NaomiPillarOfOrder extends CardImpl {

    public NaomiPillarOfOrder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever Naomi, Pillar of Order enters the battlefield or attacks, if you control an artifact and an enchantment, create a 2/2 white Samurai creature token with vigilance.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldOrAttacksSourceTriggeredAbility(new CreateTokenEffect(new SamuraiToken())),
                ControlArtifactAndEnchantmentCondition.instance, "Whenever {this} enters the battlefield or " +
                "attacks, if you control an artifact and an enchantment, create a 2/2 white Samurai creature token with vigilance."
        ).addHint(ControlArtifactAndEnchantmentHint.instance));
    }

    private NaomiPillarOfOrder(final NaomiPillarOfOrder card) {
        super(card);
    }

    @Override
    public NaomiPillarOfOrder copy() {
        return new NaomiPillarOfOrder(this);
    }
}
