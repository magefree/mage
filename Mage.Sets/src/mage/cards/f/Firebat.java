package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterOpponentsCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author NinthWorld
 */
public final class Firebat extends CardImpl {

    public Firebat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{R}");
        
        this.subtype.add(SubType.TERRAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Firebat enters the battlefield, it deals 1 damage to each creature without flying target opponent controls.
        Ability ability = new EntersBattlefieldTriggeredAbility(new FirebatEffect());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    public Firebat(final Firebat card) {
        super(card);
    }

    @Override
    public Firebat copy() {
        return new Firebat(this);
    }
}

class FirebatEffect extends OneShotEffect {

    public FirebatEffect() {
        super(Outcome.Damage);
        staticText = "it deals 1 damage to each creature without flying target opponent controls";
    }

    public FirebatEffect(final FirebatEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        Player target = game.getPlayer(getTargetPointer().getFirst(game, source));
        if(permanent != null && target != null) {
            FilterCreaturePermanent filter = new FilterCreaturePermanent();
            filter.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
            filter.add(new ControllerIdPredicate(target.getId()));
            source.addEffect(new DamageAllEffect(1, filter));
            return true;
        }

        return false;
    }

    @Override
    public FirebatEffect copy() {
        return new FirebatEffect(this);
    }
}