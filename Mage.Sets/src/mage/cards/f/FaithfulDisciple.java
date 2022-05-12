package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.DraftFromSpellbookEffect;
import mage.abilities.keyword.VigilanceAbility;
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
public final class FaithfulDisciple extends CardImpl {

    private static final List<String> spellbook = Collections.unmodifiableList(Arrays.asList(
            "All That Glitters",
            "Angelic Exaltation",
            "Angelic Gift",
            "Anointed Procession",
            "Authority of the Consuls",
            "Banishing Light",
            "Cathars' Crusade",
            "Cleric Class",
            "Divine Visitation",
            "Duelist's Heritage",
            "Gauntlets of Light",
            "Glorious Anthem",
            "Sigil of the Empty Throne",
            "Spectral Steel",
            "Teleportation Circle"
    ));

    public FaithfulDisciple(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // When Faithful Disciple dies, draft a card from Faithful Disciple's spellbook.
        this.addAbility(new DiesSourceTriggeredAbility(new DraftFromSpellbookEffect(spellbook)));
    }

    private FaithfulDisciple(final FaithfulDisciple card) {
        super(card);
    }

    @Override
    public FaithfulDisciple copy() {
        return new FaithfulDisciple(this);
    }
}
