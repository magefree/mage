
package mage.cards.m;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public final class ManaforgeCinder extends CardImpl {

    public ManaforgeCinder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B/R}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}: Add {B} or {R}. Activate this ability no more than three times each turn.
        this.addAbility(new LimitedTimesPerTurnActivatedAbility(Zone.BATTLEFIELD, new ManaforgeCinderManaEffect(), new ManaCostsImpl<>("{1}"), 3));

    }

    private ManaforgeCinder(final ManaforgeCinder card) {
        super(card);
    }

    @Override
    public ManaforgeCinder copy() {
        return new ManaforgeCinder(this);
    }
}

class ManaforgeCinderManaEffect extends OneShotEffect {

    public ManaforgeCinderManaEffect() {
        super(Outcome.PutManaInPool);
        this.staticText = "Add {B} or {R}";
    }

    public ManaforgeCinderManaEffect(final ManaforgeCinderManaEffect effect) {
        super(effect);
    }

    @Override
    public ManaforgeCinderManaEffect copy() {
        return new ManaforgeCinderManaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Choice manaChoice = new ChoiceImpl();
            Set<String> choices = new LinkedHashSet<>();
            choices.add("Black");
            choices.add("Red");
            manaChoice.setChoices(choices);
            manaChoice.setMessage("Select black or red mana to add");
            Mana mana = new Mana();
            if (!controller.choose(Outcome.Benefit, manaChoice, game)) {
                return false;
            }
            if (manaChoice.getChoice() == null) {
                return false;
            }
            switch (manaChoice.getChoice()) {
                case "Black":
                    mana.increaseBlack();
                    break;
                case "Red":
                    mana.increaseRed();
                    break;
            }
            controller.getManaPool().addMana(mana, game, source);
            return true;
        }
        return false;
    }
}
