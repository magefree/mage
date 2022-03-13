
package mage.cards.b;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class ButcherOfTheHorde extends CardImpl {

    public ButcherOfTheHorde(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{W}{B}");
        this.subtype.add(SubType.DEMON);

        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Sacrifice another creature: Butcher of the Horde gains your choice of vigilance, lifelink, or haste until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new ButcherOfTheHordeEffect(), new SacrificeTargetCost(
                        new TargetControlledCreaturePermanent(1, 1, StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE, false))));
    }

    private ButcherOfTheHorde(final ButcherOfTheHorde card) {
        super(card);
    }

    @Override
    public ButcherOfTheHorde copy() {
        return new ButcherOfTheHorde(this);
    }
}

class ButcherOfTheHordeEffect extends OneShotEffect {

    ButcherOfTheHordeEffect() {
        super(Outcome.AddAbility);
        staticText = "{this} gains your choice of vigilance, lifelink, or haste until end of turn";
    }

    ButcherOfTheHordeEffect(final ButcherOfTheHordeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (sourceObject != null && controller != null) {
            Choice abilityChoice = new ChoiceImpl();
            abilityChoice.setMessage("Choose an ability to add");

            Set<String> abilities = new HashSet<>();
            abilities.add(VigilanceAbility.getInstance().getRule());
            abilities.add(LifelinkAbility.getInstance().getRule());
            abilities.add(HasteAbility.getInstance().getRule());
            abilityChoice.setChoices(abilities);
            controller.choose(Outcome.AddAbility, abilityChoice, game);
            if (!abilityChoice.isChosen()) {
                return false;
            }
            String chosen = abilityChoice.getChoice();
            Ability ability = null;
            if (VigilanceAbility.getInstance().getRule().equals(chosen)) {
                ability = VigilanceAbility.getInstance();
            } else if (LifelinkAbility.getInstance().getRule().equals(chosen)) {
                ability = LifelinkAbility.getInstance();
            } else if (HasteAbility.getInstance().getRule().equals(chosen)) {
                ability = HasteAbility.getInstance();
            }

            if (ability != null) {
                game.informPlayers(sourceObject.getLogName() + ": " + controller.getLogName() + " has chosen: " + chosen);
                ContinuousEffect effect = new GainAbilitySourceEffect(ability, Duration.EndOfTurn);
                game.addEffect(effect, source);
                return true;
            }
        }
        return false;
    }

    @Override
    public ButcherOfTheHordeEffect copy() {
        return new ButcherOfTheHordeEffect(this);
    }

}
