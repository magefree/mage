package mage.cards.c;

import java.util.UUID;

import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CovenCondition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileAttachedEffect;
import mage.abilities.effects.common.PreventAllDamageByAttachedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.hint.common.CovenHint;
import mage.abilities.keyword.DefenderAbility;
import mage.constants.*;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.Ability;
import mage.abilities.effects.common.AttachEffect;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;

/**
 *
 * @author weirddan455
 */
public final class Candletrap extends CardImpl {

    public Candletrap(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Enchanted creature has defender.
        this.addAbility(new SimpleStaticAbility(new GainAbilityAttachedEffect(DefenderAbility.getInstance(), AttachmentType.AURA)));

        // Prevent all combat damage that would be dealt by enchanted creature.
        this.addAbility(new SimpleStaticAbility(new PreventAllDamageByAttachedEffect(Duration.WhileOnBattlefield, "enchanted creature", true)));

        // Coven — {2}{W}, Sacrifice Candletrap: Exile enchanted creature. Activate only if you control three or more creatures with different powers.
        ability = new ActivateIfConditionActivatedAbility(Zone.BATTLEFIELD, new ExileAttachedEffect(), new ManaCostsImpl<>("{2}{W}"), CovenCondition.instance);
        ability.addCost(new SacrificeSourceCost());
        ability.setAbilityWord(AbilityWord.COVEN);
        ability.addHint(CovenHint.instance);
        this.addAbility(ability);
    }

    private Candletrap(final Candletrap card) {
        super(card);
    }

    @Override
    public Candletrap copy() {
        return new Candletrap(this);
    }
}
