package mage.cards.l;

import mage.ConditionalMana;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.WardAbility;
import mage.abilities.mana.ConditionalAnyColorManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LeylineImmersion extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("legendary creature");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    public LeylineImmersion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}");

        this.subtype.add(SubType.AURA);

        // Enchant legendary creature
        TargetPermanent auraTarget = new TargetPermanent(filter);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature has ward {2} and "{T}: Add five mana in any combination of colors. Spend this mana only to cast spells."
        Ability ability = new SimpleStaticAbility(new GainAbilityAttachedEffect(
                new WardAbility(new GenericManaCost(2), false), AttachmentType.AURA
        ).setText("enchanted creature has ward {2}"));
        ability.addEffect(new GainAbilityAttachedEffect(
                new ConditionalAnyColorManaAbility(5, new LeylineImmersionManaBuilder()), AttachmentType.AURA
        ).setText("and \"{T}: Add five mana in any combination of colors. Spend this mana only to cast spells.\""));
        this.addAbility(ability);
    }

    private LeylineImmersion(final LeylineImmersion card) {
        super(card);
    }

    @Override
    public LeylineImmersion copy() {
        return new LeylineImmersion(this);
    }
}

class LeylineImmersionManaBuilder extends ConditionalManaBuilder {
    @Override
    public ConditionalMana build(Object... options) {
        return new LeylineImmersionConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast a spell";
    }
}

class LeylineImmersionConditionalMana extends ConditionalMana {

    public LeylineImmersionConditionalMana(Mana mana) {
        super(mana);
        staticText = "Spend this mana only to cast a spell";
        addCondition((game, source) -> source instanceof SpellAbility);
    }
}
