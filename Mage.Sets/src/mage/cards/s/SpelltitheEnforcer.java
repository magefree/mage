
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.SacrificeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class SpelltitheEnforcer extends CardImpl {

    public SpelltitheEnforcer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}{W}");
        this.subtype.add(SubType.ELEPHANT);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever an opponent casts a spell, that player sacrifices a permanent unless he or she pays {1}.
        this.addAbility(new SpellCastOpponentTriggeredAbility(
                Zone.BATTLEFIELD,
                new SpelltitheEnforcerEffect(),
                StaticFilters.FILTER_SPELL,
                false, 
                SetTargetPointer.PLAYER
        ));
    }

    public SpelltitheEnforcer(final SpelltitheEnforcer card) {
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
        this.staticText = "that player sacrifices a permanent unless he or she pays {1}";
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
            GenericManaCost cost = new GenericManaCost(1);
            if (!cost.pay(source, game, player.getId(), player.getId(), false)) {
                super.apply(game, source);
            }
            return true;
        }
        return false;
    }
}
