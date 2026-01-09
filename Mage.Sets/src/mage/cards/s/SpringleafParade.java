package mage.cards.s;

import java.util.UUID;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.game.permanent.token.ShapeshifterColorlessToken;

/**
 *
 * @author muz
 */
public final class SpringleafParade extends CardImpl {

    public SpringleafParade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{X}{G}{G}");

        // When this enchantment enters, create X 1/1 colorless Shapeshifter creature tokens with changeling.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new ShapeshifterColorlessToken(), GetXValue.instance)
            .setText("create X 1/1 colorless Shapeshifter creature tokens with changeling.")
        ));

        // Creature tokens you control have "{T}: Add one mana of any color."
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
            new AnyColorManaAbility(), Duration.WhileOnBattlefield, StaticFilters.FILTER_CREATURE_TOKENS)
        ));

    }

    private SpringleafParade(final SpringleafParade card) {
        super(card);
    }

    @Override
    public SpringleafParade copy() {
        return new SpringleafParade(this);
    }
}
