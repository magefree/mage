
package mage.cards.g;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author nantuko
 */
public final class GolemArtisan extends CardImpl {

    public GolemArtisan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}");
        this.subtype.add(SubType.GOLEM);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {2}: Target artifact creature gets +1/+1 until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostTargetEffect(1, 1, Duration.EndOfTurn), new GenericManaCost(2));
        ability.addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_CREATURE));
        this.addAbility(ability);

        // {2}: Target artifact creature gains your choice of flying, trample, or haste until end of turn.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GolemArtisanEffect(), new GenericManaCost(2));
        ability.addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_CREATURE));
        this.addAbility(ability);

    }

    private GolemArtisan(final GolemArtisan card) {
        super(card);
    }

    @Override
    public GolemArtisan copy() {
        return new GolemArtisan(this);
    }
}

class GolemArtisanEffect extends OneShotEffect {

    GolemArtisanEffect() {
        super(Outcome.AddAbility);
        staticText = "Target artifact creature gains your choice of flying, trample, or haste until end of turn";
    }

    private GolemArtisanEffect(final GolemArtisanEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(targetPointer.getFirst(game, source));
        Player playerControls = game.getPlayer(source.getControllerId());
        if (permanent != null && playerControls != null) {
            Choice abilityChoice = new ChoiceImpl();
            abilityChoice.setMessage("Choose an ability to add");

            Set<String> abilities = new HashSet<>();
            abilities.add(FlyingAbility.getInstance().getRule());
            abilities.add(TrampleAbility.getInstance().getRule());
            abilities.add(HasteAbility.getInstance().getRule());
            abilityChoice.setChoices(abilities);
            if (!playerControls.choose(Outcome.AddAbility, abilityChoice, game)) {
                return false;
            }

            String chosen = abilityChoice.getChoice();
            Ability ability = null;
            if (FlyingAbility.getInstance().getRule().equals(chosen)) {
                ability = FlyingAbility.getInstance();
            } else if (TrampleAbility.getInstance().getRule().equals(chosen)) {
                ability = TrampleAbility.getInstance();
            } else if (HasteAbility.getInstance().getRule().equals(chosen)) {
                ability = HasteAbility.getInstance();
            }

            if (ability != null) {
                ContinuousEffect effect = new GainAbilityTargetEffect(ability, Duration.EndOfTurn);
                game.addEffect(effect, source);
                return true;
            }
        }

        return false;
    }

    @Override
    public GolemArtisanEffect copy() {
        return new GolemArtisanEffect(this);
    }

}
