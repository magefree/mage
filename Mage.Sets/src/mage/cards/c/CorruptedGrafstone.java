package mage.cards.c;

import mage.Mana;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.mana.ManaEffect;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
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

    private CorruptedGrafstone(final CorruptedGrafstone card) {
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

    private CorruptedGrafstoneManaAbility(final CorruptedGrafstoneManaAbility ability) {
        super(ability);
    }

    @Override
    public CorruptedGrafstoneManaAbility copy() {
        return new CorruptedGrafstoneManaAbility(this);
    }

    @Override
    public List<Mana> getNetMana(Game game) {
        List<Mana> netMana = new ArrayList<>();
        if (game != null) {
            return ((CorruptedGrafstoneManaEffect) getEffects().get(0)).getNetMana(game, this);
        }
        return netMana;
    }
}

class CorruptedGrafstoneManaEffect extends ManaEffect {

    private final Mana computedMana;

    public CorruptedGrafstoneManaEffect() {
        super();
        computedMana = new Mana();
        this.staticText = "Choose a color of a card in your graveyard. Add one mana of that color";
    }

    private CorruptedGrafstoneManaEffect(final CorruptedGrafstoneManaEffect effect) {
        super(effect);
        this.computedMana = effect.computedMana.copy();
    }

    @Override
    public CorruptedGrafstoneManaEffect copy() {
        return new CorruptedGrafstoneManaEffect(this);
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

    @Override
    public Mana produceMana(Game game, Ability source) {
        Mana mana = new Mana();
        if (game == null) {
            return mana;
        }
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
                    if (!player.choose(Outcome.PutManaInPool, choice, game)) {
                        return mana;
                    }
                }
                switch (choice.getChoice()) {
                    case "Black":
                        mana.setBlack(1);
                        break;
                    case "Blue":
                        mana.setBlue(1);
                        break;
                    case "Red":
                        mana.setRed(1);
                        break;
                    case "Green":
                        mana.setGreen(1);
                        break;
                    case "White":
                        mana.setWhite(1);
                        break;
                }
            }
        }
        return mana;
    }

    private Mana getManaTypesInGraveyard(Game game, Ability source) {
        if (game != null && source != null && source.getControllerId() != null) {
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
