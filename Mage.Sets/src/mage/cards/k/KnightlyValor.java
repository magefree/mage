

package mage.cards.k;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.permanent.token.KnightToken;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class KnightlyValor extends CardImpl {

    public KnightlyValor (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{4}{W}");
        this.subtype.add(SubType.AURA);


        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // When Knightly Valor enters the battlefield, create a 2/2 white Knight creature token with vigilance.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new KnightToken())));

        // Enchanted creature gets +2/+2 and has vigilance.
        Effect effect = new BoostEnchantedEffect(2, 2, Duration.WhileOnBattlefield);
        effect.setText("Enchanted creature gets +2/+2");
        SimpleStaticAbility ability2 = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        effect = new GainAbilityAttachedEffect(VigilanceAbility.getInstance(), AttachmentType.AURA);
        effect.setText("and has vigilance");
        ability2.addEffect(effect);
        this.addAbility(ability2);

    }

    public KnightlyValor (final KnightlyValor card) {
        super(card);
    }

    @Override
    public KnightlyValor copy() {
        return new KnightlyValor(this);
    }

}
