package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.combat.AttacksIfAbleAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class InfectiousBloodlust extends CardImpl {

    private static final FilterCard filter = new FilterCard("card named Infectious Bloodlust");

    static {
        filter.add(new NamePredicate("Infectious Bloodlust"));
    }

    public InfectiousBloodlust(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature gets +2/+1, has haste, and attacks each turn if able.
        ability = new SimpleStaticAbility(new BoostEnchantedEffect(2, 1));
        ability.addEffect(new GainAbilityAttachedEffect(
                HasteAbility.getInstance(), AttachmentType.AURA
        ).setText(", has haste"));
        ability.addEffect(new AttacksIfAbleAttachedEffect(
                Duration.WhileOnBattlefield, AttachmentType.AURA
        ).setText(", and attacks each combat if able"));
        this.addAbility(ability);

        // When enchanted creature dies, you may search your library for a card named Infectious Bloodlust, reveal it, put it into your hand, then shuffle your library.
        this.addAbility(new DiesAttachedTriggeredAbility(new SearchLibraryPutInHandEffect(
                new TargetCardInLibrary(filter), true, true
        ), "enchanted creature", true));
    }

    private InfectiousBloodlust(final InfectiousBloodlust card) {
        super(card);
    }

    @Override
    public InfectiousBloodlust copy() {
        return new InfectiousBloodlust(this);
    }
}
