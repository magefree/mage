
package mage.cards.g;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;

/**
 * @author BursegSardaukar
 */
public final class GoblinClearcutter extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Forest");

    static {
        filter.add(new SubtypePredicate(SubType.FOREST));
    }

    public GoblinClearcutter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.subtype.add(SubType.GOBLIN);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {T}, Sacrifice a Forest: Add three mana in any combination of {R} and/or {G}.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GoblinClearCutterEffect(), new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(filter)));
        this.addAbility(ability);
    }

    public GoblinClearcutter(final GoblinClearcutter card) {
        super(card);
    }

    @Override
    public GoblinClearcutter copy() {
        return new GoblinClearcutter(this);
    }
}

class GoblinClearCutterEffect extends OneShotEffect {

    public GoblinClearCutterEffect() {
        super(Outcome.PutManaInPool);
        this.staticText = "Add 3 mana in any combination of {R} and/or {G}";
    }

    public GoblinClearCutterEffect(final GoblinClearCutterEffect effect) {
        super(effect);
    }

    @Override
    public GoblinClearCutterEffect copy() {
        return new GoblinClearCutterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            Choice manaChoice = new ChoiceImpl();
            Set<String> choices = new LinkedHashSet<>();
            choices.add("Red");
            choices.add("Green");
            manaChoice.setChoices(choices);
            manaChoice.setMessage("Select color of mana to add");

            for (int i = 0; i < 3; i++) {
                Mana mana = new Mana();
                if (!player.choose(Outcome.Benefit, manaChoice, game)) {
                    return false;
                }
                switch (manaChoice.getChoice()) {
                    case "Green":
                        mana.increaseGreen();
                        break;
                    case "Red":
                        mana.increaseRed();
                        break;
                }
                player.getManaPool().addMana(mana, game, source);
            }
            return true;
        }
        return false;
    }
}
