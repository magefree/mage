
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ReturnToHandChosenControlledPermanentCost;
import mage.abilities.costs.common.ReturnToHandFromBattlefieldSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SheepToken;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author L_J
 */
public final class Ovinomancer extends CardImpl {
    
    private static final FilterControlledLandPermanent filter = new FilterControlledLandPermanent("basic lands");

    static {
        filter.add(SuperType.BASIC.getPredicate());
    }

    public Ovinomancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // When Ovinomancer enters the battlefield, sacrifice it unless you return three basic lands you control to their owner's hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SacrificeSourceUnlessPaysEffect(new ReturnToHandChosenControlledPermanentCost(new TargetControlledPermanent(3, 3, filter, true)))));
        
        // {T}, Return Ovinomancer to its owner's hand: Destroy target creature. It can't be regenerated. That creature's controller creates a 0/1 green Sheep creature token.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(true), new TapSourceCost());
        ability.addEffect(new OvinomancerEffect());
        ability.addCost(new ReturnToHandFromBattlefieldSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private Ovinomancer(final Ovinomancer card) {
        super(card);
    }

    @Override
    public Ovinomancer copy() {
        return new Ovinomancer(this);
    }
}

class OvinomancerEffect extends OneShotEffect {

    public OvinomancerEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "That creature's controller creates a 0/1 green Sheep creature token";
    }

    private OvinomancerEffect(final OvinomancerEffect effect) {
        super(effect);
    }

    @Override
    public OvinomancerEffect copy() {
        return new OvinomancerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID targetId = getTargetPointer().getFirst(game, source);
        if (targetId != null) {
            Permanent permanent = game.getPermanentOrLKIBattlefield(targetId);
            if (permanent != null) {
                UUID controllerId = permanent.getControllerId();
                if (controllerId != null) {
                    new SheepToken().putOntoBattlefield(1, game, source, controllerId);
                    return true;
                }
            }
        }
        return false;
    }
}
