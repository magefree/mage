
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.RequirementEffect;
import mage.abilities.effects.common.combat.AttacksIfAbleAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.watchers.common.AttackedThisTurnWatcher;

/**
 *
 * @author fireshoes
 */
public final class Instigator extends CardImpl {

    public Instigator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SPELLSHAPER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}{B}{B}, {tap}, Discard a card: Creatures target player controls attack this turn if able.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new InstigatorEffect(), new ManaCostsImpl<>("{1}{B}{B}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new DiscardCardCost());
        ability.addTarget(new TargetPlayer());
        ability.addWatcher(new AttackedThisTurnWatcher());
        this.addAbility(ability);
    }

    private Instigator(final Instigator card) {
        super(card);
    }

    @Override
    public Instigator copy() {
        return new Instigator(this);
    }
}

class InstigatorEffect extends OneShotEffect {

    public InstigatorEffect() {
        super(Outcome.Detriment);
        staticText = "Creatures target player controls attack this turn if able";
    }

    public InstigatorEffect(final InstigatorEffect effect) {
        super(effect);
    }

    @Override
    public InstigatorEffect copy() {
        return new InstigatorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (player != null) {
            FilterCreaturePermanent filter = new FilterCreaturePermanent();
            filter.add(new ControllerIdPredicate(player.getId()));
            RequirementEffect effect = new AttacksIfAbleAllEffect(filter, Duration.EndOfTurn);
            game.addEffect(effect, source);
            return true;
        }
        return false;
    }
}
