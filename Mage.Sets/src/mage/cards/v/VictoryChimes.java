
package mage.cards.v;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.ChoosePlayerEffect;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.effects.common.continuous.UntapSourceDuringEachOtherPlayersUntapStepEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
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

    public VictoryChimes(final VictoryChimes card) {
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

    public VictoryChimesManaEffect(final VictoryChimesManaEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer((UUID) game.getState().getValue(source.getSourceId() + "_player"));
        if (player != null) {
            checkToFirePossibleEvents(getMana(game, source), game, source);
            player.getManaPool().addMana(getMana(game, source), game, source);
            return true;
        }
        return false;
    }

    @Override
    public Mana produceMana(boolean netMana, Game game, Ability source) {
        return Mana.ColorlessMana(1);
    }

    @Override
    public VictoryChimesManaEffect copy() {
        return new VictoryChimesManaEffect(this);
    }

}
