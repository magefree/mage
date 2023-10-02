package mage.cards.a;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.filter.predicate.permanent.TokenPredicate;

/**
 *
 * @author L_J
 */
public final class ApocalypseChime extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("nontoken permanents with a name originally printed in the Homelands expansion");

    static {
        // Homelands names per CR 206.3c
        List<String> nameStrings = Arrays.asList("Abbey Gargoyles", "Abbey Matron", "Aether Storm", "Aliban's Tower", "Ambush", "Ambush Party", "Anaba Ancestor", "Anaba Bodyguard", "Anaba Shaman", "Anaba Spirit Crafter", "An-Havva Constable", "An-Havva Inn", "An-Havva Township", "An-Zerrin Ruins", "Apocalypse Chime", "Autumn Willow", "Aysen Abbey", "Aysen Bureaucrats", "Aysen Crusader", "Aysen Highway", "Baki's Curse", "Baron Sengir", "Beast Walkers", "Black Carriage", "Broken Visage", "Carapace", "Castle Sengir", "Cemetery Gate", "Chain Stasis", "Chandler", "Clockwork Gnomes", "Clockwork Steed", "Clockwork Swarm", "Coral Reef", "Dark Maze", "Daughter of Autumn", "Death Speakers", "Didgeridoo", "Drudge Spell", "Dry Spell", "Dwarven Pony", "Dwarven Sea Clan", "Dwarven Trader", "Ebony Rhino", "Eron the Relentless", "Evaporate", "Faerie Noble", "Feast of the Unicorn", "Feroz's Ban", "Folk of An-Havva", "Forget", "Funeral March", "Ghost Hounds", "Giant Albatross", "Giant Oyster", "Grandmother Sengir", "Greater Werewolf", "Hazduhr the Abbot", "Headstone", "Heart Wolf", "Hungry Mist", "Ihsan's Shade", "Irini Sengir", "Ironclaw Curse", "Jinx", "Joven", "Joven's Ferrets", "Joven's Tools", "Koskun Falls", "Koskun Keep", "Labyrinth Minotaur", "Leaping Lizard", "Leeches", "Mammoth Harness", "Marjhan", "Memory Lapse", "Merchant Scroll", "Mesa Falcon", "Mystic Decree", "Narwhal", "Orcish Mine", "Primal Order", "Prophecy", "Rashka the Slayer", "Reef Pirates", "Renewal", "Retribution", "Reveka, Wizard Savant", "Root Spider", "Roots", "Roterothopter", "Rysorian Badger", "Samite Alchemist", "Sea Sprite", "Sea Troll", "Sengir Autocrat", "Sengir Bats", "Serra Aviary", "Serra Bestiary", "Serra Inquisitors", "Serra Paladin", "Serrated Arrows", "Shrink", "Soraya the Falconer", "Spectral Bears", "Timmerian Fiends", "Torture", "Trade Caravan", "Truce", "Veldrane of Sengir", "Wall of Kelp", "Willow Faerie", "Willow Priestess", "Winter Sky", "Wizards' School");
        List<NamePredicate> namePredicates = new ArrayList<>();
        for (String name: nameStrings) {
            namePredicates.add(new NamePredicate(name));
        }
        filter.add(TokenPredicate.FALSE);
        filter.add(Predicates.or(namePredicates));
    }

    public ApocalypseChime(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");

        // {2}, {T}, Sacrifice Apocalypse Chime: Destroy all nontoken permanents with a name originally printed in the Homelands expansion. They can't be regenerated.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyAllEffect(filter, true), new ManaCostsImpl<>("{2}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private ApocalypseChime(final ApocalypseChime card) {
        super(card);
    }

    @Override
    public ApocalypseChime copy() {
        return new ApocalypseChime(this);
    }
}
