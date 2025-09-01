package mage.cards.s;

import mage.abilities.common.OneOrMoreCombatDamagePlayerTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.hint.common.ArtifactYouControlHint;
import mage.abilities.keyword.TransformAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class StormTheVault extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            new FilterControlledArtifactPermanent("you control five or more artifacts"), ComparisonType.MORE_THAN, 4
    );

    public StormTheVault(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);

        this.secondSideCardClazz = mage.cards.v.VaultOfCatlacan.class;

        // Whenever one or more creatures you control deal combat damage to a player, create a colorless Treasure artifact token with "{T}, Sacrifice this artifact: Add one mana of any color."
        this.addAbility(new OneOrMoreCombatDamagePlayerTriggeredAbility(new CreateTokenEffect(new TreasureToken())));

        // At the beginning of your end step, if you control five or more artifacts, transform Storm the Vault.
        this.addAbility(new TransformAbility());
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new TransformSourceEffect())
                .withInterveningIf(condition).addHint(ArtifactYouControlHint.instance));
    }

    private StormTheVault(final StormTheVault card) {
        super(card);
    }

    @Override
    public StormTheVault copy() {
        return new StormTheVault(this);
    }
}
