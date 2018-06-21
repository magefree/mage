package mage.cards.o;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.mana.SimpleManaAbility;
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
 *
 * @author LevelX2
 */
public final class OrcishLumberjack extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("a Forest");

    static {
        filter.add(new SubtypePredicate(SubType.FOREST));
    }

    public OrcishLumberjack(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");
        this.subtype.add(SubType.ORC);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}, Sacrifice a Forest: Add three mana in any combination of {R} and/or {G}.
        Ability ability = new SimpleManaAbility(Zone.BATTLEFIELD, new OrcishLumberjackManaEffect(), new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(filter)));
        this.addAbility(ability);

    }

    public OrcishLumberjack(final OrcishLumberjack card) {
        super(card);
    }

    @Override
    public OrcishLumberjack copy() {
        return new OrcishLumberjack(this);
    }
}

class OrcishLumberjackManaEffect extends ManaEffect {

    private List<Mana> netMana = new ArrayList<>();

    public OrcishLumberjackManaEffect() {
        super();
        this.staticText = "Add three mana in any combination of {R} and/or {G}";
        netMana.add(new Mana(0, 3, 0, 0, 0, 0, 0, 0));
        netMana.add(new Mana(1, 2, 0, 0, 0, 0, 0, 0));
        netMana.add(new Mana(2, 1, 0, 0, 0, 0, 0, 0));
        netMana.add(new Mana(3, 0, 0, 0, 0, 0, 0, 0));
    }

    public OrcishLumberjackManaEffect(final OrcishLumberjackManaEffect effect) {
        super(effect);
    }

    @Override
    public OrcishLumberjackManaEffect copy() {
        return new OrcishLumberjackManaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            checkToFirePossibleEvents(getMana(game, source), game, source);
            controller.getManaPool().addMana(getMana(game, source), game, source);
            return true;
        }
        return false;
    }

    @Override
    public List<Mana> getNetMana(Game game, Ability source) {
        return netMana;
    }

    @Override
    public Mana produceMana(boolean netMana, Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            Choice manaChoice = new ChoiceImpl();
            Set<String> choices = new LinkedHashSet<>();
            choices.add("Red");
            choices.add("Green");
            manaChoice.setChoices(choices);
            manaChoice.setMessage("Select color of mana to add");

            Mana mana = new Mana();
            for (int i = 0; i < 3; i++) {
                if (!player.choose(Outcome.Benefit, manaChoice, game)) {
                    return null;
                }
                switch (manaChoice.getChoice()) {
                    case "Green":
                        mana.increaseGreen();
                        break;
                    case "Red":
                        mana.increaseRed();
                        break;
                }
            }
            return mana;
        }
        return null;
    }

}
