package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.common.continuous.LoseAbilityAttachedEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SkyTether extends CardImpl {

    public SkyTether(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature has defender and loses flying.
        ability = new SimpleStaticAbility(new GainAbilityAttachedEffect(
                DefenderAbility.getInstance(), AttachmentType.AURA, Duration.WhileOnBattlefield
        ));
        ability.addEffect(new LoseAbilityAttachedEffect(
                FlyingAbility.getInstance(), AttachmentType.AURA
        ).concatBy("and").setText("loses flying"));
        this.addAbility(ability);
    }

    private SkyTether(final SkyTether card) {
        super(card);
    }

    @Override
    public SkyTether copy() {
        return new SkyTether(this);
    }
}
