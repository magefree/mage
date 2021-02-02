package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToACreatureTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FlayedNim extends CardImpl {

    public FlayedNim(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.SKELETON);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Flayed Nim deals combat damage to a creature, that creature's controller loses that much life.
        this.addAbility(new DealsCombatDamageToACreatureTriggeredAbility(new FlayedNimEffect(), false, true));

        // {2}{B}: Regenerate Flayed Nim.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new ManaCostsImpl("{2}{B}")));
    }

    private FlayedNim(final FlayedNim card) {
        super(card);
    }

    @Override
    public FlayedNim copy() {
        return new FlayedNim(this);
    }
}

class FlayedNimEffect extends OneShotEffect {

    FlayedNimEffect() {
        super(Outcome.Benefit);
        this.staticText = "that creature's controller loses that much life";
    }

    FlayedNimEffect(final FlayedNimEffect effect) {
        super(effect);
    }

    @Override
    public FlayedNimEffect copy() {
        return new FlayedNimEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (creature == null) {
            return false;
        }
        Player player = game.getPlayer(creature.getControllerId());
        if (player == null) {
            return false;
        }
        int damage = (int) this.getValue("damage");
        player.loseLife(damage, game, source, false);
        return true;
    }
}
