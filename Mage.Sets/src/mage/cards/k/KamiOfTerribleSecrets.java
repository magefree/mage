package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.ControlArtifactAndEnchantmentCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.hint.common.ControlArtifactAndEnchantmentHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KamiOfTerribleSecrets extends CardImpl {

    public KamiOfTerribleSecrets(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // When Kami of Terrible Secrets enters the battlefield, if you control an artifact and an enchantment, you draw a card and you gain 1 life.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1)),
                ControlArtifactAndEnchantmentCondition.instance, "When {this} enters the battlefield, " +
                "if you control an artifact and an enchantment, you draw a card and you gain 1 life."
        );
        ability.addEffect(new GainLifeEffect(1));
        this.addAbility(ability.addHint(ControlArtifactAndEnchantmentHint.instance));
    }

    private KamiOfTerribleSecrets(final KamiOfTerribleSecrets card) {
        super(card);
    }

    @Override
    public KamiOfTerribleSecrets copy() {
        return new KamiOfTerribleSecrets(this);
    }
}
