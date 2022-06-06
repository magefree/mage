package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.watchers.common.SpellsCastWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RugOfSmothering extends CardImpl {

    public RugOfSmothering(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever a player casts a spell, they lose 1 life for each spell they've cast this turn.
        this.addAbility(new SpellCastAllTriggeredAbility(
                new RugOfSmotheringEffect(), StaticFilters.FILTER_SPELL_A,
                false, SetTargetPointer.PLAYER
        ), new SpellsCastWatcher());
    }

    private RugOfSmothering(final RugOfSmothering card) {
        super(card);
    }

    @Override
    public RugOfSmothering copy() {
        return new RugOfSmothering(this);
    }
}

class RugOfSmotheringEffect extends OneShotEffect {

    RugOfSmotheringEffect() {
        super(Outcome.Benefit);
        staticText = "they lose 1 life for each spell they've cast this turn";
    }

    private RugOfSmotheringEffect(final RugOfSmotheringEffect effect) {
        super(effect);
    }

    @Override
    public RugOfSmotheringEffect copy() {
        return new RugOfSmotheringEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player == null) {
            return false;
        }
        int count = game
                .getState()
                .getWatcher(SpellsCastWatcher.class)
                .getSpellsCastThisTurn(player.getId())
                .size();
        return count > 0 && player.loseLife(count, game, source, false) > 0;
    }
}
