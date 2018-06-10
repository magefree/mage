
package mage.cards.c;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.Mana;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author spjspj
 */
public final class CorruptedGrafstone extends CardImpl {

    public CorruptedGrafstone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // Corrupted Grafstone enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Choose a color of a card in your graveyard. Add one mana of that color.
        this.addAbility(new CorruptedGrafstoneManaAbility());
    }

    public CorruptedGrafstone(final CorruptedGrafstone card) {
        super(card);
    }

    @Override
    public CorruptedGrafstone copy() {
        return new CorruptedGrafstone(this);
    }
}

class CorruptedGrafstoneManaAbility extends ActivatedManaAbilityImpl {

    public CorruptedGrafstoneManaAbility() {
        super(Zone.BATTLEFIELD, new CorruptedGrafstoneManaEffect(), new TapSourceCost());
    }

    public CorruptedGrafstoneManaAbility(final CorruptedGrafstoneManaAbility ability) {
        super(ability);
    }

    @Override
    public CorruptedGrafstoneManaAbility copy() {
        return new CorruptedGrafstoneManaAbility(this);
    }

    @Override
    public List<Mana> getNetMana(Game game) {
        return ((CorruptedGrafstoneManaEffect) getEffects().get(0)).getNetMana(game, this);
    }
}

class CorruptedGrafstoneManaEffect extends ManaEffect {

    private final Mana computedMana;

    public CorruptedGrafstoneManaEffect() {
        super();
        computedMana = new Mana();
        this.staticText = "Choose a color of a card in your graveyard. Add one mana of that color";
    }

    public CorruptedGrafstoneManaEffect(final CorruptedGrafstoneManaEffect effect) {
        super(effect);
        this.computedMana = effect.computedMana.copy();
    }

    @Override
    public CorruptedGrafstoneManaEffect copy() {
        return new CorruptedGrafstoneManaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            checkToFirePossibleEvents(getMana(game, source), game, source);
            player.getManaPool().addMana(getMana(game, source), game, source);
            return true;
        }
        return false;
    }

    @Override
    public Mana produceMana(boolean netMana, Game game, Ability source) {
        Mana types = getManaTypesInGraveyard(game, source);
        Choice choice = new ChoiceColor(true);
        choice.getChoices().clear();
        choice.setMessage("Pick a mana color");
        if (types.getBlack() > 0) {
            choice.getChoices().add("Black");
        }
        if (types.getRed() > 0) {
            choice.getChoices().add("Red");
        }
        if (types.getBlue() > 0) {
            choice.getChoices().add("Blue");
        }
        if (types.getGreen() > 0) {
            choice.getChoices().add("Green");
        }
        if (types.getWhite() > 0) {
            choice.getChoices().add("White");
        }
        if (!choice.getChoices().isEmpty()) {
            Player player = game.getPlayer(source.getControllerId());
            if (player != null) {
                if (choice.getChoices().size() == 1) {
                    choice.setChoice(choice.getChoices().iterator().next());
                } else {
                    if (!player.choose(outcome, choice, game)) {
                        return null;
                    }
                }
                Mana computedManaHere = new Mana();
                switch (choice.getChoice()) {
                    case "Black":
                        computedManaHere.setBlack(1);
                        break;
                    case "Blue":
                        computedManaHere.setBlue(1);
                        break;
                    case "Red":
                        computedManaHere.setRed(1);
                        break;
                    case "Green":
                        computedManaHere.setGreen(1);
                        break;
                    case "White":
                        computedManaHere.setWhite(1);
                        break;
                }
                return computedManaHere;
            }
        }
        return null;
    }

    @Override
    public List<Mana> getNetMana(Game game, Ability source) {
        List<Mana> netManas = new ArrayList<>();
        Mana types = getManaTypesInGraveyard(game, source);
        if (types == null) {
            return null;
        }
        if (types.getBlack() > 0) {
            netManas.add(new Mana(ColoredManaSymbol.B));
        }
        if (types.getRed() > 0) {
            netManas.add(new Mana(ColoredManaSymbol.R));
        }
        if (types.getBlue() > 0) {
            netManas.add(new Mana(ColoredManaSymbol.U));
        }
        if (types.getGreen() > 0) {
            netManas.add(new Mana(ColoredManaSymbol.G));
        }
        if (types.getWhite() > 0) {
            netManas.add(new Mana(ColoredManaSymbol.W));
        }
        return netManas;
    }

    private Mana getManaTypesInGraveyard(Game game, Ability source) {

        if (source != null && source.getControllerId() != null) {
            Player controller = game.getPlayer(source.getControllerId());
            Mana types = new Mana();

            if (controller != null) {
                for (Card card : controller.getGraveyard().getCards(game)) {
                    if (card != null) {

                        for (ObjectColor color : card.getColor(game).getColors()) {
                            if (color.isWhite()) {
                                types.setWhite(1);
                            }
                            if (color.isBlue()) {
                                types.setBlue(1);
                            }
                            if (color.isBlack()) {
                                types.setBlack(1);
                            }
                            if (color.isRed()) {
                                types.setRed(1);
                            }
                            if (color.isGreen()) {
                                types.setGreen(1);
                            }
                        }
                    }
                }
                return types;
            }
        }

        return null;
    }
}
