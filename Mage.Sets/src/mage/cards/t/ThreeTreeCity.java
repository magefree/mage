package mage.cards.t;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.abilities.effects.mana.ManaEffect;
import mage.abilities.hint.ValueHint;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceColor;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.ChosenSubtypePredicate;
import mage.game.Game;
import mage.players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class ThreeTreeCity extends CardImpl {

    public ThreeTreeCity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        this.supertype.add(SuperType.LEGENDARY);

        // As Three Tree City enters, choose a creature type.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseCreatureTypeEffect(Outcome.PutManaInPool)));

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {2}, {T}: Choose a color. Add an amount of mana of that color equal to the number of creatures you control of the chosen type.
        this.addAbility(new ThreeTreeCityManaAbility());
    }

    private ThreeTreeCity(final ThreeTreeCity card) {
        super(card);
    }

    @Override
    public ThreeTreeCity copy() {
        return new ThreeTreeCity(this);
    }
}

class ThreeTreeCityManaAbility extends ActivatedManaAbilityImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("Creatures you control of the chosen type");

    static {
        filter.add(ChosenSubtypePredicate.TRUE);
    }

    ThreeTreeCityManaAbility() {
        super(Zone.BATTLEFIELD, new ThreeTreeCityManaEffect(), new GenericManaCost(2));
        this.addCost(new TapSourceCost());
        this.addHint(new ValueHint("Creatures you control of the chosen type", new PermanentsOnBattlefieldCount(filter)));
    }

    private ThreeTreeCityManaAbility(final ThreeTreeCityManaAbility ability) {
        super(ability);
    }

    @Override
    public ThreeTreeCityManaAbility copy() {
        return new ThreeTreeCityManaAbility(this);
    }
}

class ThreeTreeCityManaEffect extends ManaEffect {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("Creatures you control of the chosen type");

    static {
        filter.add(ChosenSubtypePredicate.TRUE);
    }

    ThreeTreeCityManaEffect() {
        super();
        this.staticText = "Choose a color. Add an amount of mana of that color equal to the number of creatures you control of the chosen type";
    }

    private ThreeTreeCityManaEffect(final ThreeTreeCityManaEffect effect) {
        super(effect);
    }

    @Override
    public ThreeTreeCityManaEffect copy() {
        return new ThreeTreeCityManaEffect(this);
    }

    @Override
    public List<Mana> getNetMana(Game game, Ability source) {
        if (game == null || !game.inCheckPlayableState()) {
            return new ArrayList<>();
        }

        int count = game.getBattlefield().count(filter, source.getControllerId(), source, game);
        List<Mana> netMana = new ArrayList<>();
        if (count > 0) {
            netMana.add(Mana.AnyMana(count));
        }
        return netMana;
    }

    @Override
    public Mana produceMana(Game game, Ability source) {
        if (game == null) {
            return null;
        }
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return null;
        }

        int creatures = game.getBattlefield().count(filter, source.getControllerId(), source, game);
        if (creatures == 0) {
            return null;
        }

        ChoiceColor choice = new ChoiceColor();
        choice.setMessage("Choose the color of mana to add");
        if (!controller.choose(outcome, choice, game)) {
            return null;
        }

        ManaType chosenType = ManaType.findByName(choice.getChoice());
        return chosenType == null ? null : new Mana(chosenType, creatures);
    }
}
