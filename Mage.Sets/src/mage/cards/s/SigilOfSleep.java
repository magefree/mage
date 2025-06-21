package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerAttachedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.ThatPlayerControlsTargetAdjuster;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class SigilOfSleep extends CardImpl {
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature that player controls");

    public SigilOfSleep(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Whenever enchanted creature deals damage to a player, return target creature that player controls to its owner's hand.
        Effect effect = new ReturnToHandTargetEffect();
        ability = new DealsDamageToAPlayerAttachedTriggeredAbility(effect, "enchanted", false, true, false);
        ability.addTarget(new TargetPermanent(filter));
        ability.setTargetAdjuster(new ThatPlayerControlsTargetAdjuster());
        this.addAbility(ability);
    }

    private SigilOfSleep(final SigilOfSleep card) {
        super(card);
    }

    @Override
    public SigilOfSleep copy() {
        return new SigilOfSleep(this);
    }
}
