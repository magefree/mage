package mage.cards.s;

import mage.Mana;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.DealCombatDamageControlledTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.ArtifactYouControlCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.mana.DynamicManaEffect;
import mage.abilities.hint.common.ArtifactYouControlHint;
import mage.abilities.mana.AnyColorManaAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class StormTheVault extends TransformingDoubleFacedCard {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            new FilterControlledArtifactPermanent("you control five or more artifacts"), ComparisonType.MORE_THAN, 4
    );

    public StormTheVault(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ENCHANTMENT}, new SubType[]{}, "{2}{U}{R}",
                "Vault of Catlacan",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.LAND}, new SubType[]{}, ""
        );

        // Whenever one or more creatures you control deal combat damage to a player, create a colorless Treasure artifact token with "{T}, Sacrifice this artifact: Add one mana of any color."
        this.getLeftHalfCard().addAbility(new DealCombatDamageControlledTriggeredAbility(new CreateTokenEffect(new TreasureToken())));

        // At the beginning of your end step, if you control five or more artifacts, transform Storm the Vault.
        this.getLeftHalfCard().addAbility(new BeginningOfEndStepTriggeredAbility(
                new TransformSourceEffect(), TargetController.YOU, condition, false
        ).addHint(ArtifactYouControlHint.instance));

        // Vault of Catlacan
        // <i>(Transforms from Storm the Vault.)</i>
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new InfoEffect("<i>(Transforms from Storm the Vault.)</i>")));

        // {T}: Add one mana of any color.
        this.getRightHalfCard().addAbility(new AnyColorManaAbility());

        // {T}: Add {U} for each artifact you control.
        this.getRightHalfCard().addAbility(new SimpleManaAbility(
                Zone.BATTLEFIELD,
                new DynamicManaEffect(Mana.BlueMana(1), ArtifactYouControlCount.instance),
                new TapSourceCost()
        ).addHint(ArtifactYouControlHint.instance));
    }


    private StormTheVault(final StormTheVault card) {
        super(card);
    }

    @Override
    public StormTheVault copy() {
        return new StormTheVault(this);
    }
}
