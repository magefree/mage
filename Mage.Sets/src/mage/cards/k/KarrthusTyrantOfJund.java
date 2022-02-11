
package mage.cards.k;

import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public final class KarrthusTyrantOfJund extends CardImpl {
    
    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("Dragon creatures you control");
    
    static {
        filter.add(AnotherPredicate.instance);
        filter.add(SubType.DRAGON.getPredicate());
    }

    public KarrthusTyrantOfJund(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}{R}{G}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // Haste
        this.addAbility(HasteAbility.getInstance());
        
        // When Karrthus, Tyrant of Jund enters the battlefield, gain control of all Dragons, then untap all Dragons.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new KarrthusEffect()));
        
        // Other Dragon creatures you control have haste.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(HasteAbility.getInstance(), Duration.WhileOnBattlefield, filter, true)));
        
    }

    private KarrthusTyrantOfJund(final KarrthusTyrantOfJund card) {
        super(card);
    }

    @Override
    public KarrthusTyrantOfJund copy() {
        return new KarrthusTyrantOfJund(this);
    }
}

class KarrthusEffect extends OneShotEffect {

    public KarrthusEffect() {
        super(Outcome.GainControl);
        this.staticText = "gain control of all Dragons, then untap all Dragons";
    }

    public KarrthusEffect(final KarrthusEffect effect) {
        super(effect);
    }

    @Override
    public KarrthusEffect copy() {
        return new KarrthusEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        FilterPermanent filter = new FilterPermanent();
        filter.add(SubType.DRAGON.getPredicate());
        List<Permanent> dragons = game.getBattlefield().getAllActivePermanents(filter, game);
        for (Permanent dragon : dragons) {
            ContinuousEffect effect = new KarrthusControlEffect(source.getControllerId());
            effect.setTargetPointer(new FixedTarget(dragon.getId(), game));
            game.addEffect(effect, source);
        }
        for (Permanent dragon : dragons) {
            dragon.untap(game);
        }
        return true;
    }
}

class KarrthusControlEffect extends ContinuousEffectImpl {

    private UUID controllerId;

    public KarrthusControlEffect(UUID controllerId) {
        super(Duration.EndOfGame, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
        this.controllerId = controllerId;
    }

    public KarrthusControlEffect(final KarrthusControlEffect effect) {
        super(effect);
        this.controllerId = effect.controllerId;
    }

    @Override
    public KarrthusControlEffect copy() {
        return new KarrthusControlEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent dragon = game.getPermanent(targetPointer.getFirst(game, source));
        if (dragon != null && controllerId != null) {
            return dragon.changeControllerId(controllerId, game, source);
        }
        return false;
    }
}
