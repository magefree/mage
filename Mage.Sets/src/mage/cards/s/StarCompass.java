
package mage.cards.s;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.Mana;
import mage.abilities.Abilities;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SupertypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author anonymous
 */
public final class StarCompass extends CardImpl {

    public StarCompass(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // Star Compass enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        // {tap}: Add one mana of any color that a basic land you control could produce.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, new StarCompassManaEffect(), new TapSourceCost()));
    }

    public StarCompass(final StarCompass card) {
        super(card);
    }

    @Override
    public StarCompass copy() {
        return new StarCompass(this);
    }
}

class StarCompassManaEffect extends ManaEffect {

    private static final FilterControlledPermanent filter = new FilterControlledLandPermanent();

    static {
        filter.add(new SupertypePredicate(SuperType.BASIC));
    }

    public StarCompassManaEffect() {
        super();
        staticText = "Add one mana of any type that a basic land you control could produce";
    }

    public StarCompassManaEffect(final StarCompassManaEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Mana types = getManaTypes(game, source);
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
        if (types.getColorless() > 0) {
            choice.getChoices().add("Colorless");
        }
        if (types.getAny() > 0) {
            choice.getChoices().add("Black");
            choice.getChoices().add("Red");
            choice.getChoices().add("Blue");
            choice.getChoices().add("Green");
            choice.getChoices().add("White");
            choice.getChoices().add("Colorless");
        }
        if (!choice.getChoices().isEmpty()) {
            Player player = game.getPlayer(source.getControllerId());
            if (choice.getChoices().size() == 1) {
                choice.setChoice(choice.getChoices().iterator().next());
            } else {
                if (!player.choose(outcome, choice, game)) {
                    return false;
                }
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
                    case "Colorless":
                        mana.setColorless(1);
                        break;
                }
                checkToFirePossibleEvents(mana, game, source);
                player.getManaPool().addMana(mana, game, source);
                return true;
            }
            return false;
        }
        return true;
    }

    @Override
    public Mana produceMana(boolean netMana, Game game, Ability source) {
        Mana types = getManaTypes(game, source);
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
        if (types.getColorless() > 0) {
            choice.getChoices().add("Colorless");
        }
        if (types.getAny() > 0) {
            choice.getChoices().add("Black");
            choice.getChoices().add("Red");
            choice.getChoices().add("Blue");
            choice.getChoices().add("Green");
            choice.getChoices().add("White");
            choice.getChoices().add("Colorless");
        }
        if (!choice.getChoices().isEmpty()) {
            Player player = game.getPlayer(source.getControllerId());
            if (choice.getChoices().size() == 1) {
                choice.setChoice(choice.getChoices().iterator().next());
            } else {
                if (!player.choose(outcome, choice, game)) {
                    return null;
                }
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
                    case "Colorless":
                        mana.setColorless(1);
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
        if (types.getGeneric() > 0) {
            netManas.add(Mana.ColorlessMana(1));
        }
        return netManas;
    }

    private Mana getManaTypes(Game game, Ability source) {
        List<Permanent> lands = game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game);
        Mana types = new Mana();
        for (Permanent land : lands) {
            Abilities<ActivatedManaAbilityImpl> manaAbilities = land.getAbilities().getActivatedManaAbilities(Zone.BATTLEFIELD);
            for (ActivatedManaAbilityImpl ability : manaAbilities) {
                if (!ability.equals(source) && ability.definesMana(game)) {
                    for (Mana netMana : ability.getNetMana(game)) {
                        types.add(netMana);
                    }
                }
            }
        }
        return types;
    }

    @Override
    public StarCompassManaEffect copy() {
        return new StarCompassManaEffect(this);
    }
}
