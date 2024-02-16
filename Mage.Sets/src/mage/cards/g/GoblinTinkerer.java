
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
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
 * @author LevelX2
 */
public final class GoblinTinkerer extends CardImpl {

    public GoblinTinkerer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {R}, {T}: Destroy target artifact. That artifact deals damage equal to its converted mana cost to Goblin Tinkerer.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(), new ManaCostsImpl<>("{R}"));
        ability.addEffect(new GoblinTinkererDamageEffect());
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetArtifactPermanent());
        this.addAbility(ability);
        
    }

    private GoblinTinkerer(final GoblinTinkerer card) {
        super(card);
    }

    @Override
    public GoblinTinkerer copy() {
        return new GoblinTinkerer(this);
    }
}

class GoblinTinkererDamageEffect extends OneShotEffect {
    
    public GoblinTinkererDamageEffect() {
        super(Outcome.Detriment);
        this.staticText = "That artifact deals damage equal to its mana value to {this}";
    }
    
    private GoblinTinkererDamageEffect(final GoblinTinkererDamageEffect effect) {
        super(effect);
    }
    
    @Override
    public GoblinTinkererDamageEffect copy() {
        return new GoblinTinkererDamageEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent targetArtifact = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);        
        if (controller != null && targetArtifact != null) {
            Permanent sourceObject = game.getPermanent(source.getSourceId());
            int damage = targetArtifact.getManaValue();
            if (sourceObject != null && damage > 0) {
                sourceObject.damage(damage, targetArtifact.getId(), source, game, false, true);
            }
            return true;
        }
        return false;
    }
}
