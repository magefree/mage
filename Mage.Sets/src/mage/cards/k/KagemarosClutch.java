
package mage.cards.k;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.dynamicvalue.common.SignInversionDynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class KagemarosClutch extends CardImpl {

    public KagemarosClutch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{B}");
        this.subtype.add(SubType.AURA);


        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.UnboostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature gets -X/-X, where X is the number of cards in your hand.
        DynamicValue xMinusValue = new SignInversionDynamicValue(CardsInControllerHandCount.instance);
        Effect effect = new BoostEnchantedEffect(xMinusValue, xMinusValue, Duration.WhileOnBattlefield);
        effect.setText("Enchanted creature gets -X/-X, where X is the number of cards in your hand");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));

    }

    private KagemarosClutch(final KagemarosClutch card) {
        super(card);
    }

    @Override
    public KagemarosClutch copy() {
        return new KagemarosClutch(this);
    }
}
