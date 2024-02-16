package mage.cards.g;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author MarcoMarin
 */
public final class GolgothianSylex extends CardImpl {

    public GolgothianSylex(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");

        // {1}, {tap}: Each nontoken permanent from the Antiquities expansion is sacrificed by its controller.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GolgothianSylexEffect(), new ManaCostsImpl<>("{1}"));
        ability.addCost(new TapSourceCost());  
        this.addAbility(ability);
    }

    private GolgothianSylex(final GolgothianSylex card) {
        super(card);
    }

    @Override
    public GolgothianSylex copy() {
        return new GolgothianSylex(this);
    }
}

class GolgothianSylexEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        // Antiquities names per CR 206.3b
        List<String> nameStrings = Arrays.asList("Amulet of Kroog", "Argivian Archaeologist", "Argivian Blacksmith", "Argothian Pixies", "Argothian Treefolk", "Armageddon Clock", "Artifact Blast", "Artifact Possession", "Artifact Ward", "Ashnod's Altar", "Ashnod's Battle Gear", "Ashnod's Transmogrant", "Atog", "Battering Ram", "Bronze Tablet", "Candelabra of Tawnos", "Circle of Protection: Artifacts", "Citanul Druid", "Clay Statue", "Clockwork Avian", "Colossus of Sardia", "Coral Helm", "Crumble", "Cursed Rack", "Damping Field", "Detonate", "Drafna's Restoration", "Dragon Engine", "Dwarven Weaponsmith", "Energy Flux", "Feldon's Cane", "Gaea's Avenger", "Gate to Phyrexia", "Goblin Artisans", "Golgothian Sylex", "Grapeshot Catapult", "Haunting Wind", "Hurkyl's Recall", "Ivory Tower", "Jalum Tome", "Martyrs of Korlis", "Mightstone", "Millstone", "Mishra's Factory", "Mishra's War Machine", "Mishra's Workshop", "Obelisk of Undoing", "Onulet", "Orcish Mechanics", "Ornithopter", "Phyrexian Gremlins", "Power Artifact", "Powerleech", "Priest of Yawgmoth", "Primal Clay", "The Rack", "Rakalite", "Reconstruction", "Reverse Polarity", "Rocket Launcher", "Sage of Lat-Nam", "Shapeshifter", "Shatterstorm", "Staff of Zegon", "Strip Mine", "Su-Chi", "Tablet of Epityr", "Tawnos's Coffin", "Tawnos's Wand", "Tawnos's Weaponry", "Tetravus", "Titania's Song", "Transmute Artifact", "Triskelion", "Urza's Avenger", "Urza's Chalice", "Urza's Mine", "Urza's Miter", "Urza's Power Plant", "Urza's Tower", "Wall of Spears", "Weakstone", "Xenic Poltergeist", "Yawgmoth Demon", "Yotian Soldier");
        List<NamePredicate> namePredicates = new ArrayList<>();
        for (String name: nameStrings) {
            namePredicates.add(new NamePredicate(name));
        }
        filter.add(TokenPredicate.FALSE);
        filter.add(Predicates.or(namePredicates));
    }

    GolgothianSylexEffect() {
        super(Outcome.Benefit);
        this.staticText = "Each nontoken permanent from the Antiquities expansion is sacrificed by its controller.";
    }

    private GolgothianSylexEffect(final GolgothianSylexEffect effect) {
        super(effect);
    }

    @Override
    public GolgothianSylexEffect copy() {
        return new GolgothianSylexEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
             permanent.sacrifice(source, game);
        }
        return true;
    }
}
