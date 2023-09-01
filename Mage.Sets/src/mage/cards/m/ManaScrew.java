package mage.cards.m;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.mana.ManaEffect;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author L_J
 */
public final class ManaScrew extends CardImpl {

    public ManaScrew(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // {1}: Flip a coin. If you win the flip, add {C}{C}. Activate this ability only any time you could cast an instant.
        this.addAbility(new ManaScrewAbility());
    }

    private ManaScrew(final ManaScrew card) {
        super(card);
    }

    @Override
    public ManaScrew copy() {
        return new ManaScrew(this);
    }
}

class ManaScrewAbility extends ActivatedManaAbilityImpl {

    public ManaScrewAbility() {
        super(Zone.BATTLEFIELD, new ManaScrewEffect(), new GenericManaCost(1));
        {
        }
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

    public ManaScrewEffect() {
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
