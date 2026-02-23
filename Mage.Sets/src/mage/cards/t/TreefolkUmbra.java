package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.ruleModifying.CombatDamageByToughnessAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.UmbraArmorAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TreefolkUmbra extends CardImpl {

    public TreefolkUmbra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature gets +0/+2 and assigns combat damage equal to its toughness rather than its power.
        Ability ability = new SimpleStaticAbility(new BoostEnchantedEffect(0, 2));
        ability.addEffect(new CombatDamageByToughnessAttachedEffect(
                null, "and assigns combat damage equal to its toughness rather than its power"
        ));
        this.addAbility(ability);

        // Umbra armor
        this.addAbility(new UmbraArmorAbility());
    }

    private TreefolkUmbra(final TreefolkUmbra card) {
        super(card);
    }

    @Override
    public TreefolkUmbra copy() {
        return new TreefolkUmbra(this);
    }
}
