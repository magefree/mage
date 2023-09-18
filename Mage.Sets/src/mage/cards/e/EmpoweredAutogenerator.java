package mage.cards.e;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.mana.ManaEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EmpoweredAutogenerator extends CardImpl {

    public EmpoweredAutogenerator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // Empowered Autogenerator enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Put a charge counter on Empowered Autogenerator. Add X mana of any one color, where X is the number of charge counters on Empowered Autogenerator.
        // Empowered Autogenerator's activated ability is a mana ability. It doesn’t use the stack and can’t be responded to. (2019-08-23)
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, new EmpoweredAutogeneratorManaEffect(), new TapSourceCost()));
    }

    private EmpoweredAutogenerator(final EmpoweredAutogenerator card) {
        super(card);
    }

    @Override
    public EmpoweredAutogenerator copy() {
        return new EmpoweredAutogenerator(this);
    }
}

class EmpoweredAutogeneratorManaEffect extends ManaEffect {

    private final Mana computedMana;

    EmpoweredAutogeneratorManaEffect() {
        super();
        computedMana = new Mana();
        this.staticText = "Put a charge counter on {this}. Add X mana of any one color, "
                + "where X is the number of charge counters on {this}";
    }

    private EmpoweredAutogeneratorManaEffect(final EmpoweredAutogeneratorManaEffect effect) {
        super(effect);
        this.computedMana = effect.computedMana.copy();
    }

    @Override
    public EmpoweredAutogeneratorManaEffect copy() {
        return new EmpoweredAutogeneratorManaEffect(this);
    }

    @Override
    public List<Mana> getNetMana(Game game, Ability source) {
        List<Mana> netMana = new ArrayList<>();
        if (game != null) {
            Permanent sourcePermanent = game.getPermanent(source.getSourceId());
            if (sourcePermanent != null) {
                int counters = sourcePermanent.getCounters(game).getCount(CounterType.CHARGE) + 1; // one counter will be added on real mana call
                if (counters > 0) {
                    netMana.add(Mana.AnyMana(counters));
                }
            }
        }
        return netMana;
    }

    @Override
    public Mana produceMana(Game game, Ability source) {
        Mana mana = new Mana();
        if (game == null) {
            return mana;
        }
        game.getState().processAction(game);
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (sourcePermanent == null) {
            return mana;
        }

        sourcePermanent.addCounters(CounterType.CHARGE.createInstance(), source.getControllerId(), source, game);
        int counters = sourcePermanent.getCounters(game).getCount(CounterType.CHARGE);
        if (counters == 0) {
            return mana;
        }
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return mana;
        }
        ChoiceColor choice = new ChoiceColor();
        choice.setMessage("Choose a color to add mana of that color");
        if (!controller.choose(outcome, choice, game)) {
            return mana;
        }
        if (choice.getChoice() == null) {
            return mana;
        }
        String color = choice.getChoice();
        switch (color) {
            case "Red":
                mana.setRed(counters);
                break;
            case "Blue":
                mana.setBlue(counters);
                break;
            case "White":
                mana.setWhite(counters);
                break;
            case "Black":
                mana.setBlack(counters);
                break;
            case "Green":
                mana.setGreen(counters);
                break;
        }
        return mana;
    }

}
