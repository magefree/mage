package mage.cards.e;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.ManaEffect;
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
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, new AstralCornucopiaManaEffect(), new TapSourceCost()));
    }

    private EmpoweredAutogenerator(final EmpoweredAutogenerator card) {
        super(card);
    }

    @Override
    public EmpoweredAutogenerator copy() {
        return new EmpoweredAutogenerator(this);
    }
}

class AstralCornucopiaManaEffect extends ManaEffect {

    private final Mana computedMana;

    AstralCornucopiaManaEffect() {
        super();
        computedMana = new Mana();
        this.staticText = "Put a charge counter on {this}. Add X mana of any one color, " +
                "where X is the number of charge counters on {this}";
    }

    private AstralCornucopiaManaEffect(final AstralCornucopiaManaEffect effect) {
        super(effect);
        this.computedMana = effect.computedMana.copy();
    }

    @Override
    public AstralCornucopiaManaEffect copy() {
        return new AstralCornucopiaManaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        checkToFirePossibleEvents(getMana(game, source), game, source);
        controller.getManaPool().addMana(getMana(game, source), game, source);
        return true;

    }

    @Override
    public Mana produceMana(boolean netMana, Game game, Ability source) {
        Mana mana = new Mana();
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (sourcePermanent == null) {
            return mana;
        }
        sourcePermanent.addCounters(CounterType.CHARGE.createInstance(), source, game);
        game.applyEffects();
        int counters = sourcePermanent.getCounters(game).getCount(CounterType.CHARGE);
        if (counters <= 0) {
            return mana;
        }
        if (netMana) {
            return new Mana(0, 0, 0, 0, 0, 0, counters, 0);
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
