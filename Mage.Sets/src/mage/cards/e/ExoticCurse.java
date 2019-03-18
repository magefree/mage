
package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.DomainValue;
import mage.abilities.dynamicvalue.common.SignInversionDynamicValue;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.target.TargetPermanent;

/**
 *
 * @author FenrisulfrX
 */
public final class ExoticCurse extends CardImpl {

    public ExoticCurse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.UnboostCreature));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));

        // Domain - Enchanted creature gets -1/-1 for each basic land type among lands you control.
        DynamicValue unboost = new SignInversionDynamicValue(new DomainValue());
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(unboost, unboost, Duration.WhileOnBattlefield));
        ability.setAbilityWord(AbilityWord.DOMAIN);
        this.addAbility(ability);
    }

    public ExoticCurse(final ExoticCurse card) {
        super(card);
    }

    @Override
    public ExoticCurse copy() {
        return new ExoticCurse(this);
    }
}
