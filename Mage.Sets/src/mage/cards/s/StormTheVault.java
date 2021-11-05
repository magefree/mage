
package mage.cards.s;

import java.util.UUID;

import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.ControlledCreaturesDealCombatDamagePlayerTriggeredAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.game.permanent.token.TreasureToken;

/**
 * @author LevelX2
 */
public final class StormTheVault extends CardImpl {

    public StormTheVault(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}{R}");

        this.addSuperType(SuperType.LEGENDARY);

        this.secondSideCardClazz = mage.cards.v.VaultOfCatlacan.class;

        // Whenever one or more creatures you control deal combat damage to a player, create a colorless Treasure artifact token with "{T}, Sacrifice this artifact: Add one mana of any color."
        this.addAbility(new ControlledCreaturesDealCombatDamagePlayerTriggeredAbility(new CreateTokenEffect(new TreasureToken())));

        // At the beginning of your end step, if you control five or more artifacts, transform Storm the Vault.
        this.addAbility(new TransformAbility());
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfEndStepTriggeredAbility(new TransformSourceEffect(), TargetController.YOU, false),
                new PermanentsOnTheBattlefieldCondition(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT, ComparisonType.MORE_THAN, 4),
                "At the beginning of your end step, if you control five or more artifacts, transform {this}"));

    }


    private StormTheVault(final StormTheVault card) {
        super(card);
    }

    @Override
    public StormTheVault copy() {
        return new StormTheVault(this);
    }
}