package mage.cards.m;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.mana.ConditionalColoredManaAbility;
import mage.abilities.mana.builder.common.InstantOrSorcerySpellManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MemoryOfToshiro extends CardImpl {

    public MemoryOfToshiro(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SAMURAI);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);
        this.color.setBlack(true);
        this.nightCard = true;

        // {T}, Pay 1 life: Add {B}. Spend this mana only to cast an instant or sorcery spell.
        Ability ability = new ConditionalColoredManaAbility(Mana.BlackMana(1), new InstantOrSorcerySpellManaBuilder());
        ability.addCost(new PayLifeCost(1));
        this.addAbility(ability);
    }

    private MemoryOfToshiro(final MemoryOfToshiro card) {
        super(card);
    }

    @Override
    public MemoryOfToshiro copy() {
        return new MemoryOfToshiro(this);
    }
}
