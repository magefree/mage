package mage.cards.f;

import mage.abilities.common.DealCombatDamageControlledTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.permanent.token.FaerieDragonToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FeywildVisitor extends CardImpl {

    public FeywildVisitor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BACKGROUND);

        // Commander creatures you own have "Whenever one or more nontoken creatures you control deal combat damage to a player, you create a 1/1 blue Faerie Dragon creature token with flying."
        this.addAbility(new SimpleStaticAbility(new GainAbilityAllEffect(
                new DealCombatDamageControlledTriggeredAbility(new CreateTokenEffect(new FaerieDragonToken()), StaticFilters.FILTER_CREATURE_NON_TOKEN)
                        .setTriggerPhrase("Whenever one or more nontoken creatures you control deal combat damage to a player, you "),
                Duration.WhileOnBattlefield,
                StaticFilters.FILTER_CREATURES_OWNED_COMMANDER
        )));
    }

    private FeywildVisitor(final FeywildVisitor card) {
        super(card);
    }

    @Override
    public FeywildVisitor copy() {
        return new FeywildVisitor(this);
    }
}
