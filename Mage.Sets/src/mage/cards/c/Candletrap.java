package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CovenCondition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ExileAttachedEffect;
import mage.abilities.effects.common.PreventAllDamageByAttachedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.hint.common.CovenHint;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
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
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature has defender.
        this.addAbility(new SimpleStaticAbility(new GainAbilityAttachedEffect(DefenderAbility.getInstance(), AttachmentType.AURA)));

        // Prevent all combat damage that would be dealt by enchanted creature.
        this.addAbility(new SimpleStaticAbility(new PreventAllDamageByAttachedEffect(
                Duration.WhileOnBattlefield, "enchanted creature", true
        )));

        // Coven — {2}{W}, Sacrifice Candletrap: Exile enchanted creature. Activate only if you control three or more creatures with different powers.
        Ability ability = new ActivateIfConditionActivatedAbility(
                new ExileAttachedEffect(), new ManaCostsImpl<>("{2}{W}"), CovenCondition.instance
        );
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability.setAbilityWord(AbilityWord.COVEN).addHint(CovenHint.instance));
    }

    private Candletrap(final Candletrap card) {
        super(card);
    }

    @Override
    public Candletrap copy() {
        return new Candletrap(this);
    }
}
