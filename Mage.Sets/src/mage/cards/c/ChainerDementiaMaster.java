
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileAllEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.continuous.BecomesColorTargetEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTypeTargetEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author emerald000
 */
public final class ChainerDementiaMaster extends CardImpl {
    
    private static final FilterCreaturePermanent filterCreature = new FilterCreaturePermanent("Nightmare creatures");
    private static final FilterPermanent filterPermanent = new FilterPermanent("Nightmares");
    static {
        filterCreature.add(SubType.NIGHTMARE.getPredicate());
        filterPermanent.add(SubType.NIGHTMARE.getPredicate());
    }

    public ChainerDementiaMaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MINION);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Nightmare creatures get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(1, 1, Duration.WhileOnBattlefield, filterCreature, false)));
        
        // {B}{B}{B}, Pay 3 life: Put target creature card from a graveyard onto the battlefield under your control. That creature is black and is a Nightmare in addition to its other creature types.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ChainerDementiaMasterEffect(), new ManaCostsImpl<>("{B}{B}{B}"));
        ability.addCost(new PayLifeCost(3));
        ability.addTarget(new TargetCardInGraveyard(new FilterCreatureCard("creature card from a graveyard")));
        this.addAbility(ability);
        
        // When Chainer, Dementia Master leaves the battlefield, exile all Nightmares.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new ExileAllEffect(filterPermanent), false));
    }

    private ChainerDementiaMaster(final ChainerDementiaMaster card) {
        super(card);
    }

    @Override
    public ChainerDementiaMaster copy() {
        return new ChainerDementiaMaster(this);
    }
}

class ChainerDementiaMasterEffect extends OneShotEffect {
    
    ChainerDementiaMasterEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Put target creature card from a graveyard onto the battlefield under your control. That creature is black and is a Nightmare in addition to its other creature types";
    }
    
    ChainerDementiaMasterEffect(final ChainerDementiaMasterEffect effect) {
        super(effect);
    }
    
    @Override
    public ChainerDementiaMasterEffect copy() {
        return new ChainerDementiaMasterEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            UUID cardId = this.getTargetPointer().getFirst(game, source);
            new ReturnFromGraveyardToBattlefieldTargetEffect().apply(game, source);
            Permanent permanent = game.getPermanent(cardId);
            if (permanent != null) {
                ContinuousEffectImpl effect = new BecomesColorTargetEffect(ObjectColor.BLACK, Duration.WhileOnBattlefield);
                effect.setTargetPointer(new FixedTarget(permanent, game));
                game.addEffect(effect, source);
                effect = new BecomesCreatureTypeTargetEffect(Duration.WhileOnBattlefield, SubType.NIGHTMARE, false);
                effect.setTargetPointer(new FixedTarget(permanent, game));
                game.addEffect(effect, source);
            }
            return true;
        }
        return false;
    }
}
