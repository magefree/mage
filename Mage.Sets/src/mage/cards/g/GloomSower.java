package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesBlockedByCreatureTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author arcox
 */
public final class GloomSower extends CardImpl {

    public GloomSower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}{B}");

        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(8);
        this.toughness = new MageInt(6);

        // Whenever Gloom Sower becomes blocked by a creature, that creature’s controller loses 2 life and you gain 2 life.
        Ability ability = new BecomesBlockedByCreatureTriggeredAbility(new GloomSowerEffect(), false);
        Effect effect = new GainLifeEffect(2);
        effect.setText("and you gain 2 life");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private GloomSower(final GloomSower card) {
        super(card);
    }

    @Override
    public GloomSower copy() {
        return new GloomSower(this);
    }
}

class GloomSowerEffect extends OneShotEffect {

    GloomSowerEffect() {
        super(Outcome.LoseLife);
        staticText = "that creature's controller loses 2 life";
    }

    private GloomSowerEffect(final GloomSowerEffect effect) {
        super(effect);
    }

    @Override
    public GloomSowerEffect copy() {
        return new GloomSowerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }

        Player player = game.getPlayer(permanent.getControllerId());
        if (player == null) {
            return false;
        }

        player.loseLife(2, game, source, false);
        return true;
    }
}
