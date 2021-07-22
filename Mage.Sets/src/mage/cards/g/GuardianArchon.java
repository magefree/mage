package mage.cards.g;

import mage.MageInt;
import mage.MageObject;
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
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
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
        game.addEffect(new GainAbilityTargetEffect(
                new GuardianArchonProtectionAbility(player.getId()), Duration.EndOfTurn
        ), source);
        game.addEffect(new GainAbilityControllerEffect(
                new GuardianArchonProtectionAbility(player.getId()), Duration.EndOfTurn
        ), source);
        return true;
    }
}

class GuardianArchonProtectionAbility extends ProtectionAbility {

    private final UUID playerId;

    public GuardianArchonProtectionAbility(UUID playerId) {
        super(new FilterCard());
        this.playerId = playerId;
    }

    public GuardianArchonProtectionAbility(final GuardianArchonProtectionAbility ability) {
        super(ability);
        this.playerId = ability.playerId;
    }

    @Override
    public GuardianArchonProtectionAbility copy() {
        return new GuardianArchonProtectionAbility(this);
    }

    @Override
    public String getRule() {
        return "{this} has protection from the chosen player.";
    }

    @Override
    public boolean canTarget(MageObject source, Game game) {
        if (playerId != null && source != null) {
            if (source instanceof Permanent) {
                return !((Permanent) source).isControlledBy(playerId);
            }
            if (source instanceof Spell) {
                return !((Spell) source).isControlledBy(playerId);
            }
            if (source instanceof StackObject) {
                return !((StackObject) source).isControlledBy(playerId);
            }
            if (source instanceof Card) { // e.g. for Vengeful Pharaoh
                return !((Card) source).isOwnedBy(playerId);
            }
        }
        return true;
    }
}
