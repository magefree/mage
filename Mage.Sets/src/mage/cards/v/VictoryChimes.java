package mage.cards.v;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.ChoosePlayerEffect;
import mage.abilities.effects.mana.ManaEffect;
import mage.abilities.effects.common.continuous.UntapSourceDuringEachOtherPlayersUntapStepEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VictoryChimes extends CardImpl {

    public VictoryChimes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // Untap Victory Chimes during each other player's untap step.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new UntapSourceDuringEachOtherPlayersUntapStepEffect()));

        // {T}: A player of your choice adds {C}.
        ManaEffect effect = new VictoryChimesManaEffect("chosen player");
        effect.setText("a player of your choice adds {C}");
        Ability ability = new SimpleManaAbility(Zone.BATTLEFIELD, effect, new TapSourceCost());
        // choosing player as first effect, before adding mana effect
        ability.getEffects().add(0, new ChoosePlayerEffect(Outcome.PutManaInPool).setText(""));
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

    public VictoryChimesManaEffect(String textManaPoolOwner) {
        super();
        this.staticText = "a player of your choice adds {C}";
    }

    private VictoryChimesManaEffect(final VictoryChimesManaEffect effect) {
        super(effect);
    }

    @Override
    public Player getPlayer(Game game, Ability source) {
        return game.getPlayer((UUID) game.getState().getValue(source.getSourceId() + "_player"));
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
