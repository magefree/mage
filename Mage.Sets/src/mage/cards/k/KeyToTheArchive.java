package mage.cards.k;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DraftFromSpellbookEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.abilities.effects.mana.AddManaInAnyCombinationEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KeyToTheArchive extends CardImpl {

    private static final List<String> spellbook = Collections.unmodifiableList(Arrays.asList(
            "Approach of the Second Sun",
            "Claim the Firstborn",
            "Counterspell",
            "Day of Judgment",
            "Demonic Tutor",
            "Despark",
            "Doom Blade",
            "Electrolyze",
            "Growth Spiral",
            "Krosan Grip",
            "Lightning Bolt",
            "Lightning Helix",
            "Putrefy",
            "Regrowth",
            "Time Warp"
    ));

    public KeyToTheArchive(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // Key to the Archive enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // When Key to the Archive enters the battlefield, draft a card from Key to the Archive's spellbook, then discard a card.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DraftFromSpellbookEffect(spellbook));
        ability.addEffect(new DiscardControllerEffect(1).concatBy(", then"));
        this.addAbility(ability);

        // {T}: Add two mana in any combination of colors.
        this.addAbility(new SimpleManaAbility(
                Zone.BATTLEFIELD, new AddManaInAnyCombinationEffect(2), new TapSourceCost()
        ));
    }

    private KeyToTheArchive(final KeyToTheArchive card) {
        super(card);
    }

    @Override
    public KeyToTheArchive copy() {
        return new KeyToTheArchive(this);
    }
}
