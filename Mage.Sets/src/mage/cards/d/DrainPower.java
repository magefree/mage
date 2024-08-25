package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TargetPlayerActivatesAllManaAbilitiesEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.ManaPoolItem;
import mage.players.Player;
import mage.target.TargetPlayer;

import java.util.List;
import java.util.UUID;

/**
 * @author L_J
 */
public final class DrainPower extends CardImpl {

    public DrainPower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{U}{U}");

        // Target player activates a mana ability of each land they control. Then that player loses all unspent mana and you add the mana lost this way.
        this.getSpellAbility().addEffect(new DrainPowerEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private DrainPower(final DrainPower card) {
        super(card);
    }

    @Override
    public DrainPower copy() {
        return new DrainPower(this);
    }
}

class DrainPowerEffect extends OneShotEffect {

    DrainPowerEffect() {
        super(Outcome.PutManaInPool);
        this.staticText = "Target player activates a mana ability of each land they control. Then that player loses all unspent mana and you add the mana lost this way";
    }

    private DrainPowerEffect(final DrainPowerEffect effect) {
        super(effect);
    }

    @Override
    public DrainPowerEffect copy() {
        return new DrainPowerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        if (targetPlayer == null) {
            return false;
        }
        new TargetPlayerActivatesAllManaAbilitiesEffect().setTargetPointer(this.getTargetPointer().copy())
                .apply(game, source);

        // 106.12. One card (Drain Power) causes one player to lose unspent mana and another to add “the mana lost this way.” (Note that these may be the same player.)
        // This empties the former player's mana pool and causes the mana emptied this way to be put into the latter player's mana pool. Which permanents, spells, and/or
        // abilities produced that mana are unchanged, as are any restrictions or additional effects associated with any of that mana.
        List<ManaPoolItem> manaItems = targetPlayer.getManaPool().getManaItems();
        targetPlayer.getManaPool().emptyPool(game);
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return true;
        }
        for (ManaPoolItem manaPoolItem : manaItems) {
            controller.getManaPool().addMana(
                    manaPoolItem.isConditional() ? manaPoolItem.getConditionalMana() : manaPoolItem.getMana(),
                    game, source, Duration.EndOfTurn.equals(manaPoolItem.getDuration()));
        }
        return true;
    }
}
