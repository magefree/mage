package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.combat.CantAttackBlockAttachedEffect;
import mage.abilities.effects.common.continuous.SetCardSubtypeAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FogOnTheBarrowDowns extends CardImpl {

    public FogOnTheBarrowDowns(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature is a Spirit and can't attack or block.
        Ability ability = new SimpleStaticAbility(new SetCardSubtypeAttachedEffect(
                Duration.WhileOnBattlefield, AttachmentType.AURA, SubType.SPIRIT
        ));
        ability.addEffect(new CantAttackBlockAttachedEffect(AttachmentType.AURA)
                .setText("and can't attack or block"));
        this.addAbility(ability);
    }

    private FogOnTheBarrowDowns(final FogOnTheBarrowDowns card) {
        super(card);
    }

    @Override
    public FogOnTheBarrowDowns copy() {
        return new FogOnTheBarrowDowns(this);
    }
}
