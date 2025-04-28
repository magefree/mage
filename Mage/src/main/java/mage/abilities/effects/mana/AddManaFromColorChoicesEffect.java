package mage.abilities.effects.mana;

import mage.Mana;
import mage.abilities.Ability;
import mage.choices.Choice;
import mage.constants.ManaType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public class AddManaFromColorChoicesEffect extends ManaEffect {

    private final List<ManaType> manaTypes = new ArrayList<>();
    private final List<Mana> netMana = new ArrayList<>();

    public AddManaFromColorChoicesEffect(ManaType... manaTypes) {
        super();
        for (ManaType manaType : manaTypes) {
            this.manaTypes.add(manaType);
        }
        this.manaTypes
                .stream()
                .map(CardUtil::manaTypeToColoredManaSymbol)
                .map(Mana::new)
                .forEach(netMana::add);
        staticText = "add " + CardUtil
                .concatWithOr(this.netMana.stream().map(Mana::toString).collect(Collectors.toList()));
    }

    private AddManaFromColorChoicesEffect(final AddManaFromColorChoicesEffect effect) {
        super(effect);
        this.manaTypes.addAll(effect.manaTypes);
        effect.netMana.stream().map(Mana::copy).forEach(this.netMana::add);
    }

    @Override
    public AddManaFromColorChoicesEffect copy() {
        return new AddManaFromColorChoicesEffect(this);
    }

    @Override
    public List<Mana> getNetMana(Game game, Ability source) {
        return netMana;
    }

    public List<Mana> getNetMana() {
        return netMana;
    }

    @Override
    public Mana produceMana(Game game, Ability source) {
        if (game == null || manaTypes.isEmpty()) {
            return null;
        }
        Choice choice = ManaType.getChoiceOfManaTypes(manaTypes, false);
        if (choice.getChoices().size() <= 1) {
            choice.setChoice(choice.getChoices().iterator().next());
        } else {
            Player player = game.getPlayer(source.getControllerId());
            if (player == null || !player.choose(Outcome.PutManaInPool, choice, game)) {
                return null;
            }
        }
        ManaType chosenType = ManaType.findByName(choice.getChoice());
        return chosenType == null ? null : new Mana(chosenType);
    }
}
