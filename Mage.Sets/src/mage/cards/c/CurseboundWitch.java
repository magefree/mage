package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.DraftFromSpellbookEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CurseboundWitch extends CardImpl {

    private static final List<String> spellbook = Collections.unmodifiableList(Arrays.asList(
            "Black Cat",
            "Bloodhunter Bat",
            "Cauldron Familiar",
            "Cruel Reality",
            "Curse of Leeches",
            "Expanded Anatomy",
            "Sorcerer's Broom",
            "Torment of Scarabs",
            "Trespasser's Curse",
            "Unwilling Ingredient",
            "Witch's Cauldron",
            "Witch's Cottage",
            "Witch's Familiar",
            "Witch's Oven",
            "Witch's Vengeance"
    ));

    public CurseboundWitch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // When Cursebound Witch dies, draft a card from Cursebound Witch's spellbook.
        this.addAbility(new DiesSourceTriggeredAbility(new DraftFromSpellbookEffect(spellbook)));
    }

    private CurseboundWitch(final CurseboundWitch card) {
        super(card);
    }

    @Override
    public CurseboundWitch copy() {
        return new CurseboundWitch(this);
    }
}
