package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Cindervines extends CardImpl {

    public Cindervines(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{R}{G}");

        // Whenever an opponent casts a noncreature spell, Cindervines deals 1 damage to that player.
        this.addAbility(new SpellCastOpponentTriggeredAbility(
                Zone.BATTLEFIELD, new DamageTargetEffect(1, true, "that player"),
                StaticFilters.FILTER_SPELL_A_NON_CREATURE, false, SetTargetPointer.PLAYER
        ));

        // {1}, Sacrifice Cindervines: Destroy target artifact or enchantment. Cindervines deals 2 damage to that permanent's controller.
        Ability ability = new SimpleActivatedAbility(
                new CindervinesEffect(), new GenericManaCost(1)
        );
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));
        this.addAbility(ability);
    }

    private Cindervines(final Cindervines card) {
        super(card);
    }

    @Override
    public Cindervines copy() {
        return new Cindervines(this);
    }
}

class CindervinesEffect extends OneShotEffect {

    CindervinesEffect() {
        super(Outcome.Benefit);
        staticText = "Destroy target artifact or enchantment. " +
                "{this} deals 2 damage to that permanent's controller.";
    }

    private CindervinesEffect(final CindervinesEffect effect) {
        super(effect);
    }

    @Override
    public CindervinesEffect copy() {
        return new CindervinesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        Player player = game.getPlayer(permanent.getControllerId());
        if (player == null) {
            return false;
        }
        permanent.destroy(source, game, false);
        player.damage(2, source.getSourceId(), source, game);
        return true;
    }
}
