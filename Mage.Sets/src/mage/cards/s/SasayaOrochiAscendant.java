package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.TapForManaAllTriggeredManaAbility;
import mage.abilities.costs.common.RevealHandSourceControllerCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.FlipSourceEffect;
import mage.abilities.effects.common.ManaEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceColor;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterLandCard;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TokenImpl;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class SasayaOrochiAscendant extends CardImpl {

    public SasayaOrochiAscendant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{G}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SNAKE);
        this.subtype.add(SubType.MONK);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        this.flipCard = true;
        this.flipCardName = "Sasaya's Essence";

        // Reveal your hand: If you have seven or more land cards in your hand, flip Sasaya, Orochi Ascendant.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new SasayaOrochiAscendantFlipEffect(), new RevealHandSourceControllerCost()));
    }

    public SasayaOrochiAscendant(final SasayaOrochiAscendant card) {
        super(card);
    }

    @Override
    public SasayaOrochiAscendant copy() {
        return new SasayaOrochiAscendant(this);
    }
}

class SasayaOrochiAscendantFlipEffect extends OneShotEffect {

    public SasayaOrochiAscendantFlipEffect() {
        super(Outcome.Benefit);
        this.staticText = "If you have seven or more land cards in your hand, flip {this}";
    }

    public SasayaOrochiAscendantFlipEffect(final SasayaOrochiAscendantFlipEffect effect) {
        super(effect);
    }

    @Override
    public SasayaOrochiAscendantFlipEffect copy() {
        return new SasayaOrochiAscendantFlipEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (controller.getHand().count(new FilterLandCard(), game) > 6) {
                new FlipSourceEffect(new SasayasEssence()).apply(game, source);
            }
            return true;
        }
        return false;
    }
}

class SasayasEssence extends TokenImpl {

    SasayasEssence() {
        super("Sasaya's Essence", "");
        addSuperType(SuperType.LEGENDARY);
        cardType.add(CardType.ENCHANTMENT);

        color.setGreen(true);

        // Whenever a land you control is tapped for mana, for each other land you control with the same name, add one mana of any type that land produced.
        this.addAbility(new TapForManaAllTriggeredManaAbility(
                new SasayasEssenceManaEffect(),
                new FilterControlledLandPermanent(), SetTargetPointer.PERMANENT));
    }

    public SasayasEssence(final SasayasEssence token) {
        super(token);
    }

    @Override
    public SasayasEssence copy() {
        return new SasayasEssence(this);
    }
}

class SasayasEssenceManaEffect extends ManaEffect {

    public SasayasEssenceManaEffect() {
        super();
        this.staticText = "for each other land you control with the same name, add one mana of any type that land produced";
    }

    public SasayasEssenceManaEffect(final SasayasEssenceManaEffect effect) {
        super(effect);
    }

    @Override
    public SasayasEssenceManaEffect copy() {
        return new SasayasEssenceManaEffect(this);
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
        Player controller = game.getPlayer(source.getControllerId());
        Mana mana = (Mana) this.getValue("mana");
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (controller != null && mana != null && permanent != null) {
            Mana newMana = new Mana();
            FilterPermanent filter = new FilterLandPermanent();
            filter.add(Predicates.not(new PermanentIdPredicate(permanent.getId())));
            filter.add(new NamePredicate(permanent.getName()));
            int count = game.getBattlefield().countAll(filter, controller.getId(), game);
            if (count > 0) {
                Choice choice = new ChoiceColor(true);
                choice.getChoices().clear();
                choice.setMessage("Pick the type of mana to produce");
                if (mana.getBlack() > 0) {
                    choice.getChoices().add("Black");
                }
                if (mana.getRed() > 0) {
                    choice.getChoices().add("Red");
                }
                if (mana.getBlue() > 0) {
                    choice.getChoices().add("Blue");
                }
                if (mana.getGreen() > 0) {
                    choice.getChoices().add("Green");
                }
                if (mana.getWhite() > 0) {
                    choice.getChoices().add("White");
                }
                if (mana.getColorless() > 0) {
                    choice.getChoices().add("Colorless");
                }

                if (!choice.getChoices().isEmpty()) {

                    for (int i = 0; i < count; i++) {
                        choice.clearChoice();
                        if (choice.getChoices().size() == 1) {
                            choice.setChoice(choice.getChoices().iterator().next());
                        } else {
                            if (!controller.choose(outcome, choice, game)) {
                                return null;
                            }
                        }
                        switch (choice.getChoice()) {
                            case "Black":
                                newMana.increaseBlack();
                                break;
                            case "Blue":
                                newMana.increaseBlue();
                                break;
                            case "Red":
                                newMana.increaseRed();
                                break;
                            case "Green":
                                newMana.increaseGreen();
                                break;
                            case "White":
                                newMana.increaseWhite();
                                break;
                            case "Colorless":
                                newMana.increaseColorless();
                                break;
                        }
                    }

                }
            }
            return newMana;
        }
        return null;
    }

}
