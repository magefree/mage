
package mage.cards.w;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.LoseAbilityTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author L_J
 */
public final class WalkingSponge extends CardImpl {

    public WalkingSponge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.subtype.add(SubType.SPONGE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {T}: Target creature loses your choice of flying, first strike, or trample until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new WalkingSpongeEffect(), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private WalkingSponge(final WalkingSponge card) {
        super(card);
    }

    @Override
    public WalkingSponge copy() {
        return new WalkingSponge(this);
    }
}

class WalkingSpongeEffect extends OneShotEffect {

    WalkingSpongeEffect() {
        super(Outcome.Benefit);
        this.staticText = "Target creature loses your choice of flying, first strike, or trample until end of turn";
    }

    private WalkingSpongeEffect(final WalkingSpongeEffect effect) {
        super(effect);
    }

    @Override
    public WalkingSpongeEffect copy() {
        return new WalkingSpongeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (controller != null && permanent != null) {
            ChoiceImpl chooseAbility = new ChoiceImpl();
            chooseAbility.setMessage("Choose an ability to remove (default is flying)");
            Set<String> choice = new LinkedHashSet<>();
            choice.add("Flying");
            choice.add("First strike");
            choice.add("Trample");
            chooseAbility.setChoices(choice);
            // since the player can't pick "no ability", let's default to the first option
            Ability ability = FlyingAbility.getInstance();

            if (controller.choose(Outcome.UnboostCreature, chooseAbility, game)) {
                String chosenAbility = chooseAbility.getChoice();
                if (chosenAbility.equals("First strike")) {
                    ability = FirstStrikeAbility.getInstance();
                } else if (chosenAbility.equals("Trample")) {
                    ability = TrampleAbility.getInstance();
                }
            } else {
                return false;
            }
            game.informPlayers(controller.getLogName() + " has chosen " + ability.getRule());
            ContinuousEffect effect = new LoseAbilityTargetEffect(ability, Duration.EndOfTurn);
            game.addEffect(effect, source);
            return true;
        }
        return false;
    }
}
