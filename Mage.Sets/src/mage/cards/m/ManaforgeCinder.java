
package mage.cards.m;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.mana.ManaEffect;
import mage.abilities.mana.LimitedTimesPerTurnActivatedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Susucr
 */
public final class ManaforgeCinder extends CardImpl {

    public ManaforgeCinder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B/R}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}: Add {B} or {R}. Activate this ability no more than three times each turn.
        this.addAbility(new LimitedTimesPerTurnActivatedManaAbility(
                Zone.BATTLEFIELD, new ManaforgeCinderManaEffect(),
                new ManaCostsImpl<>("{1}"), 3,
                ManaforgeCinderManaEffect.netMana
        ));

    }

    private ManaforgeCinder(final ManaforgeCinder card) {
        super(card);
    }

    @Override
    public ManaforgeCinder copy() {
        return new ManaforgeCinder(this);
    }
}

class ManaforgeCinderManaEffect extends ManaEffect {

    static final List<Mana> netMana = new ArrayList<>();

    static {
        netMana.add(new Mana(ColoredManaSymbol.R));
        netMana.add(new Mana(ColoredManaSymbol.B));
    }


    public ManaforgeCinderManaEffect() {
        super();
        this.staticText = "Add {B} or {R}";
    }

    private ManaforgeCinderManaEffect(final ManaforgeCinderManaEffect effect) {
        super(effect);
    }

    @Override
    public ManaforgeCinderManaEffect copy() {
        return new ManaforgeCinderManaEffect(this);
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
        Choice choice = new ChoiceColor(true);
        choice.getChoices().clear();
        choice.setMessage("Pick a mana color");
        choice.getChoices().add("Red");
        choice.getChoices().add("Black");

        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            if (!player.choose(outcome, choice, game)) {
                return mana;
            }
            switch (choice.getChoice()) {
                case "Black":
                    mana.setBlack(1);
                    break;
                case "Red":
                    mana.setRed(1);
                    break;
            }
        }
        return mana;
    }
}
