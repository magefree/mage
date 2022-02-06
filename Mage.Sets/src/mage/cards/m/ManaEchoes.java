
package mage.cards.m;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
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

/**
 *
 * @author LevelX2
 */
public final class ManaEchoes extends CardImpl {

    public ManaEchoes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}{R}");

        // Whenever a creature enters the battlefield, you may add X mana of {C}, where X is the number of creatures you control that share a creature type with it.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(Zone.BATTLEFIELD,
                new ManaEchoesEffect(), StaticFilters.FILTER_PERMANENT_A_CREATURE, true, SetTargetPointer.PERMANENT, ""));
    }

    private ManaEchoes(final ManaEchoes card) {
        super(card);
    }

    @Override
    public ManaEchoes copy() {
        return new ManaEchoes(this);
    }
}

class ManaEchoesEffect extends OneShotEffect {

    public ManaEchoesEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may add X mana of {C}, where X is the number of creatures you control that share a creature type with it";
    }

    public ManaEchoesEffect(final ManaEchoesEffect effect) {
        super(effect);
    }

    @Override
    public ManaEchoesEffect copy() {
        return new ManaEchoesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (controller != null && permanent != null) {
            int foundCreatures = 0;
            for (Permanent perm : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), game)) {
                if (permanent.shareCreatureTypes(game, perm)) {
                    foundCreatures++;
                }
            }
            if (foundCreatures > 0) {
                controller.getManaPool().addMana(Mana.ColorlessMana(foundCreatures), game, source);
            }
            return true;
        }
        return false;
    }
}
