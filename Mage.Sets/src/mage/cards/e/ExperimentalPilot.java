package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CrewIncreasedPowerAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DraftFromSpellbookEffect;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInHand;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * @author Svyatoslav28
 */
public final class ExperimentalPilot extends CardImpl {

    private static final List<String> spellbook = Collections.unmodifiableList(Arrays.asList(
            "Bomat Bazaar Barge",
            "Cultivator's Caravan",
            "Daredevil Dragster",
            "Demolition Stomper",
            "Futurist Sentinel",
            "High-Speed Hoverbike",
            "Mechtitan Core",
            "Mindlink Mech",
            "Mobile Garrison",
            "Ovalchase Dragster",
            "Raiders' Karve",
            "Reckoner Bankbuster",
            "Silent Submersible",
            "Thundering Chariot",
            "Untethered Express"
    ));

    public ExperimentalPilot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PILOT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Ward {2}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{2}"), false));

        // {U}, Discard two cards: Draft a card from Experimental Pilot's spellbook.
        Ability ability = new SimpleActivatedAbility(
                new DraftFromSpellbookEffect(spellbook), new ManaCostsImpl<>("{U}")
        );
        ability.addCost(new DiscardTargetCost(new TargetCardInHand(2, StaticFilters.FILTER_CARD_CARDS)));
        this.addAbility(ability);
        // Experimental Pilot crews Vehicles as though its power were 2 greater.
        this.addAbility(new CrewIncreasedPowerAbility());
    }

    private ExperimentalPilot(final ExperimentalPilot card) {
        super(card);
    }

    @Override
    public ExperimentalPilot copy() {
        return new ExperimentalPilot(this);
    }
}
