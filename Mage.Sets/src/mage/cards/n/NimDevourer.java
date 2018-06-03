
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author fireshoes
 */
public final class NimDevourer extends CardImpl {
    
    private static final FilterControlledPermanent filter = new FilterControlledPermanent("artifact you control");

    static {
        filter.add(new CardTypePredicate(CardType.ARTIFACT));
    }

    public NimDevourer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(1);

        // Nim Devourer gets +1/+0 for each artifact you control.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostSourceEffect(new PermanentsOnBattlefieldCount(filter), new StaticValue(0), Duration.WhileOnBattlefield)));
        
        // {B}{B}: Return Nim Devourer from your graveyard to the battlefield, then sacrifice a creature. Activate this ability only during your upkeep.
        Ability ability = new ConditionalActivatedAbility(Zone.GRAVEYARD, 
                new ReturnSourceFromGraveyardToBattlefieldEffect(), 
                new ManaCostsImpl("{B}{B}"), 
                new IsStepCondition(PhaseStep.UPKEEP), null);
        ability.addEffect(new NimDevourerEffect());
        this.addAbility(ability);
    }

    public NimDevourer(final NimDevourer card) {
        super(card);
    }

    @Override
    public NimDevourer copy() {
        return new NimDevourer(this);
    }
}

class NimDevourerEffect extends OneShotEffect {

    public NimDevourerEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "then sacrifice a creature";
    }

    public NimDevourerEffect(final NimDevourerEffect effect) {
        super(effect);
    }

    @Override
    public NimDevourerEffect copy() {
        return new NimDevourerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            Target target = new TargetControlledPermanent(new FilterControlledCreaturePermanent());

            if (target.canChoose(player.getId(), game) && player.choose(Outcome.Sacrifice, target, source.getSourceId(), game)) {
                Permanent permanent = game.getPermanent(target.getFirstTarget());
                if (permanent != null) {
                    return permanent.sacrifice(source.getSourceId(), game);
                }
            }
        }
        return false;
    }
}