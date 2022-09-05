package mage.cards.k;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author Alex-Vasile
 */
public class KarnsSylex extends CardImpl {
    public KarnsSylex(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{}, "");

        // Karn’s Sylex
        this.addAbility(new EntersBattlefieldTappedAbility());

        // Players can’t pay life to cast spells or to activate abilities that aren’t mana abilities.
        // TODO: AngelOfJubilation

        // {X}, {T}, Exile Karn’s Sylex: Destroy each nonland permanent with mana value X or less. Activate only as a sorcery.
        this.addAbility(new ActivateAsSorceryActivatedAbility(new KarnsSylexDestroyEffect));
    }

    private KarnsSylex(final KarnsSylex card) {
        super(card);
    }

    @Override
    public KarnsSylex copy() {
        return new KarnsSylex(this);
    }
}

class KarnsSylexDestroyEffect extends OneShotEffect {

    KarnsSylexDestroyEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "Destroy each nonland permanent with mana value X or less.";
    }

    private KarnsSylexDestroyEffect(final KarnsSylexDestroyEffect effect) {
        super(effect);
    }

    public KarnsSylexDestroyEffect copy() {
        return new KarnsSylexDestroyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        FilterNonlandPermanent filter = new FilterNonlandPermanent();
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, source.getManaCostsToPay().getX() + 1));

        boolean destroyed = false;
        for (Permanent permanent : game.getState().getBattlefield().getActivePermanents(filter, source.getControllerId(), game)) {
            destroyed |= permanent.destroy(source, game);
        }
        return destroyed;
    }
}