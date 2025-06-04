package mage.cards.v;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.continuous.UntapSourceDuringEachOtherPlayersUntapStepEffect;
import mage.abilities.effects.mana.ManaEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VictoryChimes extends CardImpl {

    public VictoryChimes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // Untap Victory Chimes during each other player's untap step.
        this.addAbility(new SimpleStaticAbility(new UntapSourceDuringEachOtherPlayersUntapStepEffect()));

        // {T}: A player of your choice adds {C}.
        ManaEffect effect = new VictoryChimesManaEffect("chosen player");
        effect.setText("a player of your choice adds {C}");
        Ability ability = new SimpleManaAbility(Zone.BATTLEFIELD, effect, new TapSourceCost());
        this.addAbility(ability);
    }

    private VictoryChimes(final VictoryChimes card) {
        super(card);
    }

    @Override
    public VictoryChimes copy() {
        return new VictoryChimes(this);
    }
}

class VictoryChimesManaEffect extends ManaEffect {

    VictoryChimesManaEffect(String textManaPoolOwner) {
        super();
        this.staticText = "a player of your choice adds {C}";
    }

    private VictoryChimesManaEffect(final VictoryChimesManaEffect effect) {
        super(effect);
    }

    @Override
    public Player getPlayer(Game game, Ability source) {
        if (!game.inCheckPlayableState()) {
            TargetPlayer target = new TargetPlayer(1, 1, true);
            if (target.choose(Outcome.PutManaInPool, source.getControllerId(), source, game)) {
                return game.getPlayer(target.getFirstTarget());
            }
        }
        return game.getPlayer(source.getControllerId()); // Count as controller's potential mana for card playability
    }

    @Override
    public Mana produceMana(Game game, Ability source) {
        return Mana.ColorlessMana(1);
    }

    @Override
    public VictoryChimesManaEffect copy() {
        return new VictoryChimesManaEffect(this);
    }

}
