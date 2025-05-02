package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
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
public final class MagebaneLizard extends CardImpl {

    public MagebaneLizard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.LIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Whenever a player casts a noncreature spell, Magebane Lizard deals damage to that player equal to the number of noncreature spells they've cast this turn.
        this.addAbility(new SpellCastAllTriggeredAbility(
                new MagebaneLizardEffect(), StaticFilters.FILTER_SPELL_A_NON_CREATURE,
                false, SetTargetPointer.PLAYER
        ));
    }

    private MagebaneLizard(final MagebaneLizard card) {
        super(card);
    }

    @Override
    public MagebaneLizard copy() {
        return new MagebaneLizard(this);
    }
}

class MagebaneLizardEffect extends OneShotEffect {

    MagebaneLizardEffect() {
        super(Outcome.Benefit);
        staticText = "{this} deals damage to that player equal to the number of noncreature spells they've cast this turn";
    }

    private MagebaneLizardEffect(final MagebaneLizardEffect effect) {
        super(effect);
    }

    @Override
    public MagebaneLizardEffect copy() {
        return new MagebaneLizardEffect(this);
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
                .stream()
                .mapToInt(spell -> !spell.isCreature(game) ? 1 : 0)
                .sum();
        return player.damage(count, source, game) > 0;
    }
}
