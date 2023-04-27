package mage.cards.f;

import java.util.UUID;

import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.keyword.VentureIntoTheDungeonEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.AttachmentType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.Ability;
import mage.abilities.effects.common.AttachEffect;
import mage.constants.Outcome;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author weirddan455
 */
public final class Fly extends CardImpl {

    public Fly(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature has flying and "Whenever this creature deals combat damage to a player, venture into the dungeon."
        ability = new SimpleStaticAbility(new GainAbilityAttachedEffect(FlyingAbility.getInstance(), AttachmentType.AURA));
        ability.addEffect(new GainAbilityAttachedEffect(
                new DealsCombatDamageToAPlayerTriggeredAbility(new VentureIntoTheDungeonEffect(), false),
                AttachmentType.AURA).setText("and \"Whenever this creature deals combat damage to a player, venture into the dungeon.\"")
        );
        this.addAbility(ability);
    }

    private Fly(final Fly card) {
        super(card);
    }

    @Override
    public Fly copy() {
        return new Fly(this);
    }
}
