
package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.DraftFromSpellbookEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * @author Svyatoslav28
 */
public final class SlimefootThallidTransplant extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("a Swamp or Forest");

    static {
        filter.add(Predicates.or(
                SubType.SWAMP.getPredicate(),
                SubType.FOREST.getPredicate()
        ));
    }

    private static final List<String> spellbook = Collections.unmodifiableList(Arrays.asList(
            "Deathbloom Thallid",
            "Deathbonnet Sprout",
            "Fungal Plots",
            "Rhizome Lurcher",
            "Saproling Migration",
            "Spore Crawler",
            "Sporecrown Thallid",
            "Sporemound",
            "Spore Swarm",
            "Swarm Shambler",
            "Thallid Omnivore",
            "Thallid Soothsayer",
            "Verdant Embrace",
            "Verdant Force",
            "Yavimaya Sapherd"
    ));

    public SlimefootThallidTransplant (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.FUNGUS);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever a Swamp or Forest enters the battlefield under your control, draft a card from Slimefoot, Thallid Transplant’s spellbook.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                new DraftFromSpellbookEffect(spellbook), filter
        ));
    }

    private SlimefootThallidTransplant(final SlimefootThallidTransplant card) {
        super(card);
    }

    @Override
    public SlimefootThallidTransplant copy() {
        return new SlimefootThallidTransplant(this);
    }
}