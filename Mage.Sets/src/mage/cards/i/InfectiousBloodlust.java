
package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.combat.AttacksIfAbleAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class InfectiousBloodlust extends CardImpl {

    private static final FilterCard filter = new FilterCard("card named Infectious Bloodlust");

    static {
        filter.add(new NamePredicate("Infectious Bloodlust"));
    }

    public InfectiousBloodlust(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{R}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Enchanted creature gets +2/+1, has haste, and attacks each turn if able.
        Effect effect = new BoostEnchantedEffect(2, 1);
        effect.setText("Enchanted creature gets +2/+1");
        ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        effect = new GainAbilityAttachedEffect(HasteAbility.getInstance(), AttachmentType.AURA);
        effect.setText("has haste");
        ability.addEffect(effect);
        effect = new AttacksIfAbleAttachedEffect(Duration.WhileOnBattlefield, AttachmentType.AURA);
        effect.setText("and attacks each turn if able");
        ability.addEffect(effect);
        this.addAbility(ability);

        // When enchanted creature dies, you may search your library for a card named Infectious Bloodlust, reveal it, put it into your hand, then shuffle your library.
        TargetCardInLibrary target = new TargetCardInLibrary(0, 1, filter);
        this.addAbility(new DiesAttachedTriggeredAbility(new SearchLibraryPutInHandEffect(target, true, true), "enchanted creature", true));
    }

    public InfectiousBloodlust(final InfectiousBloodlust card) {
        super(card);
    }

    @Override
    public InfectiousBloodlust copy() {
        return new InfectiousBloodlust(this);
    }
}
