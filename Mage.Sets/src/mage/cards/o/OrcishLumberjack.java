package mage.cards.o;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.mana.ManaEffect;
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
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;

import java.util.*;

/**
 * @author LevelX2
 */
public final class OrcishLumberjack extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("a Forest");

    static {
        filter.add(SubType.FOREST.getPredicate());
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

    private OrcishLumberjack(final OrcishLumberjack card) {
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
        netMana.add(new Mana(0, 0, 0, 0, 3, 0, 0, 0));
        netMana.add(new Mana(0, 0, 0, 1, 2, 0, 0, 0));
        netMana.add(new Mana(0, 0, 0, 2, 1, 0, 0, 0));
        netMana.add(new Mana(0, 0, 0, 3, 0, 0, 0, 0));
    }

    private OrcishLumberjackManaEffect(final OrcishLumberjackManaEffect effect) {
        super(effect);
        netMana.addAll(effect.netMana);
    }

    @Override
    public OrcishLumberjackManaEffect copy() {
        return new OrcishLumberjackManaEffect(this);
    }

    @Override
    public List<Mana> getNetMana(Game game, Ability source) {
        return netMana;
    }

    @Override
    public Mana produceMana(Game game, Ability source) {
        Mana mana = new Mana();
        if (game == null) {
            return mana;
        }
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            Choice manaChoice = new ChoiceImpl();
            Set<String> choices = new LinkedHashSet<>();
            choices.add("Red");
            choices.add("Green");
            manaChoice.setChoices(choices);
            manaChoice.setMessage("Select color of mana to add");
            for (int i = 0; i < 3; i++) {
                if (!player.choose(Outcome.Benefit, manaChoice, game)) {
                    return mana;
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
        }
        return mana;
    }

}
