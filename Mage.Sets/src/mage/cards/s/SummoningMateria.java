package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.AttachedToMatchesFilterCondition;
import mage.abilities.decorator.ConditionalAsThoughEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.common.continuous.LookAtTopCardOfLibraryAnyTimeEffect;
import mage.abilities.effects.common.continuous.PlayFromTopOfLibraryEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SummoningMateria extends CardImpl {

    private static final Condition condition = new AttachedToMatchesFilterCondition(StaticFilters.FILTER_PERMANENT_CREATURE);

    public SummoningMateria(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{G}");

        this.subtype.add(SubType.EQUIPMENT);

        // You may look at the top card of your library any time.
        this.addAbility(new SimpleStaticAbility(new LookAtTopCardOfLibraryAnyTimeEffect()));

        // As long as this Equipment is attached to a creature, you may cast creature spells from the top of your library.
        this.addAbility(new SimpleStaticAbility(new ConditionalAsThoughEffect(
                new PlayFromTopOfLibraryEffect(StaticFilters.FILTER_CARD_CREATURE), condition
        ).setText("as long as {this} is attached to a creature, you may cast creature spells from the top of your library")));

        // Equipped creature gets +2/+2 and has vigilance and "{T}: Add {G}."
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(2, 2));
        ability.addEffect(new GainAbilityAttachedEffect(
                VigilanceAbility.getInstance(), AttachmentType.EQUIPMENT
        ).setText("and has vigilance"));
        ability.addEffect(new GainAbilityAttachedEffect(
                new GreenManaAbility(), AttachmentType.EQUIPMENT
        ).setText("and \"{T}: Add {G}.\""));
        this.addAbility(ability);

        // Equip {2}
        this.addAbility(new EquipAbility(2));
    }

    private SummoningMateria(final SummoningMateria card) {
        super(card);
    }

    @Override
    public SummoningMateria copy() {
        return new SummoningMateria(this);
    }
}
