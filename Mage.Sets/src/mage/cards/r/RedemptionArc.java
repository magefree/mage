package mage.cards.r;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ExileAttachedEffect;
import mage.abilities.effects.common.combat.GoadAttachedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.constants.*;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.effects.common.AttachEffect;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;

/**
 *
 * @author jimga150
 */
public final class RedemptionArc extends CardImpl {

    public RedemptionArc(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");
        
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature has indestructible and is goaded.
        Effect effect = new GainAbilityAttachedEffect(IndestructibleAbility.getInstance(),
                AttachmentType.AURA, Duration.WhileOnBattlefield);

        Effect effect2 = new GoadAttachedEffect();

        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        ability.addEffect(effect2);
        ability.addCustomOutcome(Outcome.Protect);
        this.addAbility(ability);

        // {1}{W}: Exile enchanted creature.
        this.addAbility(new SimpleActivatedAbility(new ExileAttachedEffect(), new ManaCostsImpl<>("{1}{W}")));
    }

    private RedemptionArc(final RedemptionArc card) {
        super(card);
    }

    @Override
    public RedemptionArc copy() {
        return new RedemptionArc(this);
    }
}
