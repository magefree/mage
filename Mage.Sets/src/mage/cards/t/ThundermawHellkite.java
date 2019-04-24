
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author jeffwadsworth
 */
public final class ThundermawHellkite extends CardImpl {
    
    final static FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures with flying your opponents control");
    
    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public ThundermawHellkite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}{R}");
        this.subtype.add(SubType.DRAGON);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // Haste
        this.addAbility(HasteAbility.getInstance());
        
        // When Thundermaw Hellkite enters the battlefield, it deals 1 damage to each creature with flying your opponents control. Tap those creatures.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageAllEffect(1, "it", filter));
        ability.addEffect(new TapAllEffect(filter));
        this.addAbility(ability);
    }

    public ThundermawHellkite(final ThundermawHellkite card) {
        super(card);
    }

    @Override
    public ThundermawHellkite copy() {
        return new ThundermawHellkite(this);
    }
}

class TapAllEffect extends OneShotEffect {
    
    private FilterCreaturePermanent filter;

    public TapAllEffect(FilterCreaturePermanent filter) {
        super(Outcome.Tap);
        this.filter = filter;
        staticText = "Tap those creatures";
    }

    public TapAllEffect(final TapAllEffect effect) {
        super(effect);
        this.filter = effect.filter.copy();
    }

    @Override
    public TapAllEffect copy() {
        return new TapAllEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
            permanent.tap(game);
        }
        return true;
    }
}


