
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterAttackingOrBlockingCreature;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author jeffwadsworth
 */
public final class BrigidHeroOfKinsbaile extends CardImpl {

    public BrigidHeroOfKinsbaile(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}{W}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.KITHKIN, SubType.ARCHER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // {T}: Brigid, Hero of Kinsbaile deals 2 damage to each attacking or blocking creature target player controls.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BrigidHeroOfKinsbaileEffect(), new TapSourceCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

    }

    private BrigidHeroOfKinsbaile(final BrigidHeroOfKinsbaile card) {
        super(card);
    }

    @Override
    public BrigidHeroOfKinsbaile copy() {
        return new BrigidHeroOfKinsbaile(this);
    }
}

class BrigidHeroOfKinsbaileEffect extends OneShotEffect {

    public BrigidHeroOfKinsbaileEffect() {
        super(Outcome.Damage);
        staticText = "{this} deals 2 damage to each attacking or blocking creature target player controls";
    }

    public BrigidHeroOfKinsbaileEffect(final BrigidHeroOfKinsbaileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (targetPlayer != null) {
            for (Permanent creature : game.getBattlefield().getAllActivePermanents(new FilterAttackingOrBlockingCreature(), targetPlayer.getId(), game)) {
                creature.damage(2, source.getSourceId(), source, game, false, true);
            }
            return true;
        }
        return false;
    }

    @Override
    public BrigidHeroOfKinsbaileEffect copy() {
        return new BrigidHeroOfKinsbaileEffect(this);
    }
}
