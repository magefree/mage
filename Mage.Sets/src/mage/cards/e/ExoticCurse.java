package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.DomainValue;
import mage.abilities.dynamicvalue.common.SignInversionDynamicValue;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.hint.common.DomainHint;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author FenrisulfrX
 */
public final class ExoticCurse extends CardImpl {

    public ExoticCurse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.UnboostCreature));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));

        // Domain - Enchanted creature gets -1/-1 for each basic land type among lands you control.
        DynamicValue unboost = new SignInversionDynamicValue(DomainValue.REGULAR);
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(unboost, unboost, Duration.WhileOnBattlefield));
        ability.setAbilityWord(AbilityWord.DOMAIN);
        this.addAbility(ability.addHint(DomainHint.instance));
    }

    private ExoticCurse(final ExoticCurse card) {
        super(card);
    }

    @Override
    public ExoticCurse copy() {
        return new ExoticCurse(this);
    }
}
