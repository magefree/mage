    
package mage.cards.r;

import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.BushidoAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public final class RoninCliffrider extends CardImpl {

    public RoninCliffrider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SAMURAI);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Bushido 1
        this.addAbility(new BushidoAbility(1));
        // Whenever Ronin Cliffrider attacks, you may have it deal 1 damage to each creature defending player controls.
        this.addAbility(new AttacksTriggeredAbility(new RoninCliffriderEffect(), true));
    }

    private RoninCliffrider(final RoninCliffrider card) {
        super(card);
    }

    @Override
    public RoninCliffrider copy() {
        return new RoninCliffrider(this);
    }
}
class RoninCliffriderEffect extends OneShotEffect {

    public RoninCliffriderEffect() {
        super(Outcome.Damage);
        this.staticText = "you may have it deal 1 damage to each creature defending player controls";
    }

    public RoninCliffriderEffect(final RoninCliffriderEffect effect) {
        super(effect);
    }

    @Override
    public RoninCliffriderEffect copy() {
        return new RoninCliffriderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID defenderId = game.getCombat().getDefenderId(source.getSourceId());
        if (defenderId != null) {
            FilterCreaturePermanent filter = new FilterCreaturePermanent();
            filter.add(new ControllerIdPredicate(defenderId));
            List<Permanent> permanents = game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game);
            for (Permanent permanent : permanents) {
                permanent.damage(1, source.getSourceId(), source, game, false, true);
            }
            return true;
        }
        return false;
    }
}
