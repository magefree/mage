
package mage.cards.s;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.ChoosePlayerEffect;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Galatolol
 */
public final class SpectralSearchlight extends CardImpl {

    public SpectralSearchlight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {T}: Choose a player. That player adds one mana of any color they chooses.
        ManaEffect effect = new SpectralSearchlightManaEffect("chosen player");
        effect.setText("That player adds one mana of any color they choose");
        Ability ability = new SimpleManaAbility(Zone.BATTLEFIELD, effect, new TapSourceCost());
        // choosing player as first effect, before adding mana effect
        ability.getEffects().add(0, new ChoosePlayerEffect(Outcome.PutManaInPool));
        this.addAbility(ability);
    }

    public SpectralSearchlight(final SpectralSearchlight card) {
        super(card);
    }

    @Override
    public SpectralSearchlight copy() {
        return new SpectralSearchlight(this);
    }
}

class SpectralSearchlightManaEffect extends ManaEffect {

    public SpectralSearchlightManaEffect(String textManaPoolOwner) {
        super();
        this.staticText = (textManaPoolOwner.equals("their") ? "that player adds " : "add ") + "one mana of any color" + " to " + textManaPoolOwner + " mana pool";
    }

    public SpectralSearchlightManaEffect(final SpectralSearchlightManaEffect effect) {
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
        if (netMana) {
            return null;
        }
        UUID playerId = (UUID) game.getState().getValue(source.getSourceId() + "_player");
        Player player = game.getPlayer(playerId);
        ChoiceColor choice = new ChoiceColor();
        if (player != null && player.choose(outcome, choice, game)) {
            return choice.getMana(1);
        }
        return new Mana();
    }

    @Override
    public SpectralSearchlightManaEffect copy() {
        return new SpectralSearchlightManaEffect(this);
    }

}
