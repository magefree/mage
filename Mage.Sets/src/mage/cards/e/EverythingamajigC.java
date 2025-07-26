package mage.cards.e;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.VariableCostType;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.VariableManaCost;
import mage.abilities.effects.common.continuous.BecomesXXConstructSourceEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.effects.mana.ManaEffect;
import mage.abilities.hint.common.MyTurnHint;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Ketsuban
 */
public final class EverythingamajigC extends CardImpl {

    public EverythingamajigC(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");

        // Mana Screw
        // 1: Flip a coin. If you win the flip, add CC to your mana pool. Activate this ability only any time you could cast an instant.
        this.addAbility(new ManaScrewAbility());

        // Disrupting Scepter
        // 3, {T}: Target player discards a card. Activate this ability only during your turn.
        Ability ability = new ActivateIfConditionActivatedAbility(
                new DiscardTargetEffect(1), new GenericManaCost(3), MyTurnCondition.instance
        );
        ability.addTarget(new TargetPlayer());
        ability.addCost(new TapSourceCost());
        this.addAbility(ability.addHint(MyTurnHint.instance));

        // Chimeric Staff
        // X: Everythingamajig becomes an X/X Construct artifact creature until end of turn.
        this.addAbility(new SimpleActivatedAbility(new BecomesXXConstructSourceEffect(Duration.EndOfTurn), new VariableManaCost(VariableCostType.NORMAL)));
    }

    private EverythingamajigC(final EverythingamajigC card) {
        super(card);
    }

    @Override
    public EverythingamajigC copy() {
        return new EverythingamajigC(this);
    }
}

class ManaScrewAbility extends ActivatedManaAbilityImpl {

    public ManaScrewAbility() {
        super(Zone.BATTLEFIELD, new ManaScrewEffect(), new GenericManaCost(1));
    }

    private ManaScrewAbility(final ManaScrewAbility ability) {
        super(ability);
    }

    @Override
    public ActivationStatus canActivate(UUID playerId, Game game) {
        Player player = game.getPlayer(playerId);
        if (player != null && !player.isInPayManaMode()) {
            return super.canActivate(playerId, game);
        }
        return ActivationStatus.getFalse();
    }

    @Override
    public ManaScrewAbility copy() {
        return new ManaScrewAbility(this);
    }

    @Override
    public String getRule() {
        return super.getRule() + " Activate only as an instant.";
    }
}

class ManaScrewEffect extends ManaEffect {

    ManaScrewEffect() {
        super();
        this.staticText = "Flip a coin. If you win the flip, add {C}{C}";
    }

    private ManaScrewEffect(final ManaScrewEffect effect) {
        super(effect);
    }

    @Override
    public ManaScrewEffect copy() {
        return new ManaScrewEffect(this);
    }

    @Override
    public List<Mana> getNetMana(Game game, Ability source) {
        return new ArrayList<>();
    }

    @Override
    public Mana produceMana(Game game, Ability source) {
        if (game != null) {
            Player player = getPlayer(game, source);
            if (player != null && player.flipCoin(source, game, true)) {
                return Mana.ColorlessMana(2);
            }
        }
        return new Mana();
    }
}
