package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.DraftFromSpellbookEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.ExpansionSet;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TirelessAngler extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("an Island or Swamp");

    static {
        filter.add(Predicates.or(
                SubType.ISLAND.getPredicate(),
                SubType.SWAMP.getPredicate()
        ));
    }

    private static final List<String> spellbook;

    static {
        spellbook = new ArrayList<>(Arrays.asList(
                "Archipelagore", // mutate card
                "Fleet Swallower",
                "Moat Piranhas",
                "Mystic Skyfish",
                "Nadir Kraken",
                "Pouncing Shoreshark", // mutate card
                "Riptide Turtle",
                "Ruin Crab",
                "Sea-Dasher Octopus", // mutate card
                "Serpent of Yawning Depths",
                "Sigiled Starfish",
                "Spined Megalodon",
                "Stinging Lionfish",
                "Voracious Greatshark",
                "Wormhole Serpent"
        ));
        spellbook.removeIf(card -> ExpansionSet.HIDE_MUTATE_CARDS && ExpansionSet.MUTATE_CARD_NAMES.contains(card));
    }

    public TirelessAngler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Whenever an Island or Swamp you control enters, draft a card from Tireless Angler's spellbook.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                new DraftFromSpellbookEffect(spellbook), filter
        ));
    }

    private TirelessAngler(final TirelessAngler card) {
        super(card);
    }

    @Override
    public TirelessAngler copy() {
        return new TirelessAngler(this);
    }
}
