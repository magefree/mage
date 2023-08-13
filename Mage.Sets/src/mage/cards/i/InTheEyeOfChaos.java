package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterSpell;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.util.ManaUtil;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class InTheEyeOfChaos extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("an instant spell");

    static {
        filter.add(CardType.INSTANT.getPredicate());
    }

    public InTheEyeOfChaos(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");
        this.supertype.add(SuperType.WORLD);


        // Whenever a player casts an instant spell, counter it unless that player pays {X}, where X is its converted mana cost.
        this.addAbility(new SpellCastAllTriggeredAbility(Zone.BATTLEFIELD, new InTheEyeOfChaosEffect(), filter, false, SetTargetPointer.SPELL));
    }

    private InTheEyeOfChaos(final InTheEyeOfChaos card) {
        super(card);
    }

    @Override
    public InTheEyeOfChaos copy() {
        return new InTheEyeOfChaos(this);
    }
}

class InTheEyeOfChaosEffect extends OneShotEffect {

    InTheEyeOfChaosEffect() {
        super(Outcome.Detriment);
        this.staticText = "counter it unless that player pays {X}, where X is its mana value";
    }

    InTheEyeOfChaosEffect(final InTheEyeOfChaosEffect effect) {
        super(effect);
    }

    @Override
    public InTheEyeOfChaosEffect copy() {
        return new InTheEyeOfChaosEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        StackObject spell = game.getStack().getStackObject(targetPointer.getFirst(game, source));
        if (spell != null) {
            Player player = game.getPlayer(spell.getControllerId());
            if (player != null) {
                Cost cost = ManaUtil.createManaCost(spell.getManaValue(), true);
                if (!cost.pay(source, game, source, player.getId(), false)) {
                    game.getStack().counter(spell.getId(), source, game);
                }
                return true;
            }
        }
        return false;
    }
}
