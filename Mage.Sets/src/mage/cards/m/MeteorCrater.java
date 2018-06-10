
package mage.cards.m;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.Mana;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author anonymous
 */
public final class MeteorCrater extends CardImpl {

    public MeteorCrater(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {tap}: Choose a color of a permanent you control. Add one mana of that color.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, new MeteorCraterEffect(), new TapSourceCost()));
    }

    public MeteorCrater(final MeteorCrater card) {
        super(card);
    }

    @Override
    public MeteorCrater copy() {
        return new MeteorCrater(this);
    }
}

class MeteorCraterEffect extends ManaEffect {

    /**
     * *
     * 04/10/2004 You can't choose "colorless". You have to choose one of the
     * five colors.
     */
    private static final FilterControlledPermanent filter = new FilterControlledPermanent();

    public MeteorCraterEffect() {
        super();
        staticText = "Choose a color of a permanent you control. Add one mana of that color";
    }

    public MeteorCraterEffect(final MeteorCraterEffect effect) {
        super(effect);
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
    public Mana produceMana(boolean netMana, Game game, Ability source) {
        Mana types = getManaTypes(game, source);
        Choice choice = new ChoiceColor(true);
        choice.getChoices().clear();
        choice.setMessage("Pick a mana color");
        if (types.getAny() > 0) {
            choice.getChoices().add("Black");
            choice.getChoices().add("Red");
            choice.getChoices().add("Blue");
            choice.getChoices().add("Green");
            choice.getChoices().add("White");
        } else {
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
        }
        if (!choice.getChoices().isEmpty()) {
            Player player = game.getPlayer(source.getControllerId());
            if (choice.getChoices().size() == 1) {
                choice.setChoice(choice.getChoices().iterator().next());
            } else {
                player.choose(outcome, choice, game);
            }
            if (choice.getChoice() != null) {
                Mana mana = new Mana();
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
                return mana;
            }
        }
        return null;
    }

    @Override
    public List<Mana> getNetMana(Game game, Ability source) {
        List<Mana> netManas = new ArrayList<>();
        Mana types = getManaTypes(game, source);
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

    private Mana getManaTypes(Game game, Ability source) {
        List<Permanent> controlledPermanents = game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game);
        Mana types = new Mana();
        for (Permanent permanent : controlledPermanents) {
            ObjectColor color = permanent.getColor(game);
            if (color.isBlack()) {
                types.add(Mana.BlackMana(1));
            }
            if (color.isBlue()) {
                types.add(Mana.BlueMana(1));
            }
            if (color.isGreen()) {
                types.add(Mana.GreenMana(1));
            }
            if (color.isRed()) {
                types.add(Mana.RedMana(1));
            }
            if (color.isWhite()) {
                types.add(Mana.WhiteMana(1));
            }
        }
        return types;
    }

    @Override
    public MeteorCraterEffect copy() {
        return new MeteorCraterEffect(this);
    }
}
