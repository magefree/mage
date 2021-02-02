
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BlocksSourceTriggeredAbility;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class KaijinOfTheVanishingTouch extends CardImpl {

    public KaijinOfTheVanishingTouch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // Whenever Kaijin of the Vanishing Touch blocks a creature, return that creature to its owner's hand at end of combat.
        Effect effect = new ReturnToHandTargetEffect();
        effect.setText("return that creature to its owner's hand at end of combat");
        this.addAbility(new BlocksSourceTriggeredAbility(new CreateDelayedTriggeredAbilityEffect(new AtTheEndOfCombatDelayedTriggeredAbility(effect)), false, true));
    }

    private KaijinOfTheVanishingTouch(final KaijinOfTheVanishingTouch card) {
        super(card);
    }

    @Override
    public KaijinOfTheVanishingTouch copy() {
        return new KaijinOfTheVanishingTouch(this);
    }
}
