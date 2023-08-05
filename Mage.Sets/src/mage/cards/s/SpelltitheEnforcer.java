package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.effects.common.SacrificeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.util.ManaUtil;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SpelltitheEnforcer extends CardImpl {

    public SpelltitheEnforcer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");
        this.subtype.add(SubType.ELEPHANT);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever an opponent casts a spell, that player sacrifices a permanent unless they pay {1}.
        this.addAbility(new SpellCastOpponentTriggeredAbility(
                Zone.BATTLEFIELD,
                new SpelltitheEnforcerEffect(),
                StaticFilters.FILTER_SPELL_A,
                false,
                SetTargetPointer.PLAYER
        ));
    }

    private SpelltitheEnforcer(final SpelltitheEnforcer card) {
        super(card);
    }

    @Override
    public SpelltitheEnforcer copy() {
        return new SpelltitheEnforcer(this);
    }
}

class SpelltitheEnforcerEffect extends SacrificeEffect {

    SpelltitheEnforcerEffect() {
        super(new FilterPermanent("permanent to sacrifice"), 1, "that player");
        this.staticText = "that player sacrifices a permanent unless they pay {1}";
    }

    SpelltitheEnforcerEffect(final SpelltitheEnforcerEffect effect) {
        super(effect);
    }

    @Override
    public SpelltitheEnforcerEffect copy() {
        return new SpelltitheEnforcerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (player != null) {
            Cost cost = ManaUtil.createManaCost(1, false);
            if (!cost.pay(source, game, source, player.getId(), false)) {
                super.apply(game, source);
            }
            return true;
        }
        return false;
    }
}
