package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetArtifactPermanent;

/**
 *
 * @author BursegSardaukar
 */
public final class GoblinArchaeologist extends CardImpl {

    public GoblinArchaeologist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.ARTIFICER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {R}, {T]: Flip a coin. If you win the flip, destroy target artifact and untap Goblin Archaeologist. If you lose the flip, sacrifice Goblin Archaeologist.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GoblinArchaeologistEffect(),new ManaCostsImpl<>("{R}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetArtifactPermanent());
        this.addAbility(ability);
    }

    private GoblinArchaeologist(final GoblinArchaeologist card) {
        super(card);
    }

    @Override
    public GoblinArchaeologist copy() {
        return new GoblinArchaeologist(this);
    }
}

class GoblinArchaeologistEffect extends OneShotEffect {

    public GoblinArchaeologistEffect() {
        super(Outcome.DestroyPermanent);
    }

    public GoblinArchaeologistEffect(final GoblinArchaeologistEffect ability) {
        super(ability);
    }

    @Override
    public GoblinArchaeologistEffect copy() {
        return new GoblinArchaeologistEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
       Player player = game.getPlayer(source.getControllerId());
       Permanent permanent = game.getPermanent(source.getSourceId());
       if (player != null && permanent != null) {
           if (!player.flipCoin(source, game, true)) {
               permanent.sacrifice(source, game);
           }else{
               Permanent targetArtifact = game.getPermanent(source.getFirstTarget());
               targetArtifact.destroy(source, game, true);
               permanent.untap(game);
           }
           return true;
       }
       return false;
   }
    
    @Override
    public String getText(Mode mode) {
        return "Flip a coin. If you win the flip, destroy target artifact and untap {this}. If you lose the flip, sacrifice {this}";
    }
}
