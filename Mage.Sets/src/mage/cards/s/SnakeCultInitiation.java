
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.PoisonousAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class SnakeCultInitiation extends CardImpl {

    public SnakeCultInitiation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{B}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);
        // Enchanted creature has poisonous 3.
        this.addAbility(
                new SimpleStaticAbility(Zone.BATTLEFIELD,
                new GainAbilityAttachedEffect(new PoisonousAbility(3), AttachmentType.AURA)
                        .setText("Enchanted creature has poisonous 3. " +
                                "<i>(Whenever it deals combat damage to a player, that player gets three poison counters. " +
                                "A player with ten or more poison counters loses the game.)</i>")
        ));
    }

    private SnakeCultInitiation(final SnakeCultInitiation card) {
        super(card);
    }

    @Override
    public SnakeCultInitiation copy() {
        return new SnakeCultInitiation(this);
    }
}
