package mage.cards.b;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DraftFromSpellbookEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author Svyatoslav28
 */
public final class BoseijuPathlighter extends CardImpl {

    private static final List<String> spellbook = Collections.unmodifiableList(Arrays.asList(
            "Bonders' Enclave",
            "Boseiju, Who Endures",
            "Emergence Zone",
            "Field of Ruin",
            "Gingerbread Cabin",
            "Hall of Oracles",
            "Khalni Garden",
            "Memorial to Unity",
            "Mobilized District",
            "Radiant Fountain",
            "Roadside Reliquary",
            "Scavenger Grounds",
            "Secluded Courtyard",
            "Thriving Grove",
            "Treasure vault"
    ));

    public BoseijuPathlighter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Boseiju Pathlighter enters the battlefield, draft a card from Boseiju Pathlighter's spellbook.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DraftFromSpellbookEffect(spellbook));
        this.addAbility(ability);
    }

    private BoseijuPathlighter(final BoseijuPathlighter card) {
        super(card);
    }

    @Override
    public BoseijuPathlighter copy() {
        return new BoseijuPathlighter(this);
    }
}
