
package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.DamageEverythingEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.TargetPermanent;
import mage.target.common.TargetAnyTarget;
import mage.watchers.common.DamagedByWatcher;

/**
 *
 * @author LevelX2
 */
public final class JayaBallardTaskMage extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("blue permanent");
    
    static {
        filter.add(new ColorPredicate(ObjectColor.BLUE));
    }
    
    public JayaBallardTaskMage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SPELLSHAPER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {R}, {tap}, Discard a card: Destroy target blue permanent.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(), new ManaCostsImpl<>("{R}"));
        ability.addTarget(new TargetPermanent(filter));
        ability.addCost(new TapSourceCost());
        ability.addCost(new DiscardCardCost());        
        this.addAbility(ability);
        
        // {1}{R}, {tap}, Discard a card: Jaya Ballard, Task Mage deals 3 damage to any target. A creature dealt damage this way can't be regenerated this turn.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(3), new ManaCostsImpl<>("{1}{R}"));
        ability.addTarget(new TargetAnyTarget());
        ability.addCost(new TapSourceCost());
        ability.addCost(new DiscardCardCost()); 
        ability.addEffect(new CantRegenerateEffect());
        this.addAbility(ability, new DamagedByWatcher(false));
        
        // {5}{R}{R}, {tap}, Discard a card: Jaya Ballard deals 6 damage to each creature and each player.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageEverythingEffect(6), new ManaCostsImpl<>("{5}{R}{R}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new DiscardCardCost());        
        this.addAbility(ability);
        
    }

    private JayaBallardTaskMage(final JayaBallardTaskMage card) {
        super(card);
    }

    @Override
    public JayaBallardTaskMage copy() {
        return new JayaBallardTaskMage(this);
    }
}

class CantRegenerateEffect extends ContinuousRuleModifyingEffectImpl {

    public CantRegenerateEffect() {
        super(Duration.EndOfTurn, Outcome.Detriment);
        staticText = "A creature dealt damage this way can't be regenerated this turn";
    }

    public CantRegenerateEffect(final CantRegenerateEffect effect) {
        super(effect);
    }

    @Override
    public CantRegenerateEffect copy() {
        return new CantRegenerateEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.REGENERATE) {
            DamagedByWatcher watcher = game.getState().getWatcher(DamagedByWatcher.class, source.getSourceId());
            if (watcher != null) {
                return watcher.wasDamaged(event.getTargetId(), game);
            } 
        }
        return false;
    }

}
