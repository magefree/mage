package mage.cards.s;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.ChoosePlayerEffect;
import mage.abilities.effects.mana.ManaEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ManaChoice;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Galatolol
 */
public final class SpectralSearchlight extends CardImpl {

    public SpectralSearchlight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {T}: Choose a player. That player adds one mana of any color they chooses.
        ManaEffect effect = new SpectralSearchlightManaEffect();
        Ability ability = new SimpleManaAbility(Zone.BATTLEFIELD, effect, new TapSourceCost());
        // choosing player as first effect, before adding mana effect
        ability.getEffects().add(0, new ChoosePlayerEffect(Outcome.PutManaInPool));
        this.addAbility(ability);
    }

    private SpectralSearchlight(final SpectralSearchlight card) {
        super(card);
    }

    @Override
    public SpectralSearchlight copy() {
        return new SpectralSearchlight(this);
    }
}

class SpectralSearchlightManaEffect extends ManaEffect {

    public SpectralSearchlightManaEffect() {
        super();
        this.staticText = "That player adds one mana of any color they choose";
    }

    private SpectralSearchlightManaEffect(final SpectralSearchlightManaEffect effect) {
        super(effect);
    }

    @Override
    public Player getPlayer(Game game, Ability source) {
        return game.getPlayer((UUID) game.getState().getValue(source.getSourceId() + "_player"));
    }

    @Override
    public List<Mana> getNetMana(Game game, Ability source) {
        List<Mana> netMana = new ArrayList<>();
        netMana.add(Mana.AnyMana(1));
        return netMana;
    }

    @Override
    public Mana produceMana(Game game, Ability source) {
        if (game != null) {
            Player player = getPlayer(game, source);
            return ManaChoice.chooseAnyColor(player, game, 1);
        }
        return new Mana();
    }

    @Override
    public SpectralSearchlightManaEffect copy() {
        return new SpectralSearchlightManaEffect(this);
    }

}
