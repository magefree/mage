package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RevealSecretOpponentCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseSecretOpponentEffect;
import mage.abilities.effects.common.continuous.GainAbilityControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPlayer;
import mage.filter.predicate.other.PlayerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GuardianArchon extends CardImpl {

    public GuardianArchon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{W}");

        this.subtype.add(SubType.ARCHON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // As Guardian Archon enters the battlefield, secretly choose an opponent.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseSecretOpponentEffect()));

        // Reveal the player you chose: You and target permanent you control each gain protection from the chosen player until end of turn. Activate only once.
        Ability ability = new SimpleActivatedAbility(new GuardianArchonEffect(), new RevealSecretOpponentCost());
        ability.addTarget(new TargetControlledPermanent());
        this.addAbility(ability);
    }

    private GuardianArchon(final GuardianArchon card) {
        super(card);
    }

    @Override
    public GuardianArchon copy() {
        return new GuardianArchon(this);
    }
}

class GuardianArchonEffect extends OneShotEffect {

    GuardianArchonEffect() {
        super(Outcome.Benefit);
        staticText = "you and target permanent you control each gain protection " +
                "from the chosen player until end of turn. Activate only once";
    }

    private GuardianArchonEffect(final GuardianArchonEffect effect) {
        super(effect);
    }

    @Override
    public GuardianArchonEffect copy() {
        return new GuardianArchonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(ChooseSecretOpponentEffect.getChosenPlayer(source, game));
        if (player == null) {
            return false;
        }
        FilterPlayer filter = new FilterPlayer(player.getName());
        filter.add(new PlayerIdPredicate(player.getId()));
        game.addEffect(new GainAbilityTargetEffect(
                new ProtectionAbility(filter), Duration.EndOfTurn
        ), source);
        game.addEffect(new GainAbilityControllerEffect(
                new ProtectionAbility(filter), Duration.EndOfTurn
        ), source);
        return true;
    }
}
