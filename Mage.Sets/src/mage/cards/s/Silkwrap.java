
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.OnLeaveReturnExiledToBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

/**
 *
 * @author jeffwadsworth
 */
public final class Silkwrap extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with mana value 3 or less an opponent controls");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public Silkwrap(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{W}");

        // When Silkwrap enters the battlefield, exile target creature with converted mana cost 3 or less an opponent controls until Silkwrap leaves the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new SilkwrapEffect());
        ability.addTarget(new TargetPermanent(filter));
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new OnLeaveReturnExiledToBattlefieldAbility()));
        this.addAbility(ability);

    }

    private Silkwrap(final Silkwrap card) {
        super(card);
    }

    @Override
    public Silkwrap copy() {
        return new Silkwrap(this);
    }
}

class SilkwrapEffect extends OneShotEffect {

    public SilkwrapEffect() {
        super(Outcome.Neutral);
        this.staticText = "exile target creature with mana value 3 or less an opponent controls until {this} leaves the battlefield. <i>(That creature returns under its owner's control.)</i>";
    }

    public SilkwrapEffect(final SilkwrapEffect effect) {
        super(effect);
    }

    @Override
    public SilkwrapEffect copy() {
        return new SilkwrapEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            return new ExileTargetEffect(CardUtil.getCardExileZoneId(game, source), permanent.getIdName()).apply(game, source);
        }
        return false;
    }
}
