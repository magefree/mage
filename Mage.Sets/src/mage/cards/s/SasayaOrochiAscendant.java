package mage.cards.s;

import java.util.ArrayList;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.TapForManaAllTriggeredManaAbility;
import mage.abilities.costs.common.RevealHandSourceControllerCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.FlipSourceEffect;
import mage.abilities.effects.mana.ManaEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
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

import java.util.List;
import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.choices.Choice;
import mage.choices.ChoiceColor;

/**
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
        Effect effect = new SasayaOrochiAscendantFlipEffect();
        effect.setOutcome(Outcome.AIDontUseIt);  // repetition issues need to be fixed for the AI to use this effectively
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new SasayaOrochiAscendantFlipEffect(), new RevealHandSourceControllerCost()));
    }

    private SasayaOrochiAscendant(final SasayaOrochiAscendant card) {
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
    public List<Mana> getNetMana(Game game, Ability source) {
        List<Mana> netMana = new ArrayList<>();
        Player controller = game.getPlayer(source.getControllerId());
        Mana producedMana = (Mana) this.getValue("mana");
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (controller != null && producedMana != null && permanent != null) {
            FilterPermanent filter = new FilterLandPermanent();
            filter.add(Predicates.not(new PermanentIdPredicate(permanent.getId())));
            filter.add(new NamePredicate(permanent.getName()));
            int count = game.getBattlefield().countAll(filter, controller.getId(), game);
            if (count > 0) {
               if (producedMana.getBlack() > 0) {
                   netMana.add(Mana.BlackMana(count));
                }
                if (producedMana.getRed() > 0) {
                   netMana.add(Mana.RedMana(count));
                }
                if (producedMana.getBlue() > 0) {
                    netMana.add(Mana.BlueMana(count));
                }
                if (producedMana.getGreen() > 0) {
                    netMana.add(Mana.GreenMana(count));
                }
                if (producedMana.getWhite() > 0) {
                    netMana.add(Mana.WhiteMana(count));
                }
                if (producedMana.getColorless() > 0) {
                    netMana.add(Mana.ColorlessMana(count));
                }                
            }
        }
        return netMana;

    }

    /**
     * RULINGS 6/1/2005 If Sasaya’s Essence’s controller has four Forests and
     * taps one of them for Green, the Essence will add GreenGreenGreen to that
     * player’s mana pool for a total of GreenGreenGreenGreen.
     *
     * 6/1/2005 If Sasaya’s Essence’s controller has four Mossfire Valley and
     * taps one of them for RedGreen, the Essence will add three mana (one for
     * each other Mossfire Valley) of any combination of Red and/or Green to
     * that player’s mana pool.
     *
     * 6/1/2005 If Sasaya’s Essence’s controller has two Brushlands and taps one
     * of them for White, Sasaya’s Essence adds another White to that player’s
     * mana pool. It won’t produce Green or Colorless unless the land was tapped
     * for Green or Colorless instead.
     */
    @Override
    public Mana produceMana(Game game, Ability source) {
        Mana newMana = new Mana();
        if (game == null) {
            return newMana;
        }
        Player controller = game.getPlayer(source.getControllerId());
        Mana mana = (Mana) this.getValue("mana");
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (controller != null && mana != null && permanent != null) {
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
                                return newMana;
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
        }
        return newMana;
    }
}
