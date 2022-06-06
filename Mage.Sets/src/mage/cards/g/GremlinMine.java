
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetArtifactPermanent;

/**
 *
 * @author North
 */
public final class GremlinMine extends CardImpl {

    private static final FilterArtifactPermanent filterCreature = new FilterArtifactPermanent("artifact creature");
    private static final FilterArtifactPermanent filterNonCreature = new FilterArtifactPermanent("noncreature artifact");

    static {
        filterCreature.add(CardType.CREATURE.getPredicate());
        filterNonCreature.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    public GremlinMine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // {1}, {tap}, Sacrifice Gremlin Mine: Gremlin Mine deals 4 damage to target artifact creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(4), new ManaCostsImpl<>("{1}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetArtifactPermanent(filterCreature));
        this.addAbility(ability);
        // {1}, {tap}, Sacrifice Gremlin Mine: Remove up to four charge counters from target noncreature artifact.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GremlinMineEffect(), new ManaCostsImpl<>("{1}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetArtifactPermanent(filterNonCreature));
        this.addAbility(ability);
    }

    private GremlinMine(final GremlinMine card) {
        super(card);
    }

    @Override
    public GremlinMine copy() {
        return new GremlinMine(this);
    }
}

class GremlinMineEffect extends OneShotEffect {

    public GremlinMineEffect() {
        super(Outcome.Detriment);
        this.staticText = "Remove up to four charge counters from target noncreature artifact";
    }

    public GremlinMineEffect(GremlinMineEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getFirstTarget());

        if (player != null && permanent != null) {
            int existingCount = permanent.getCounters(game).getCount(CounterType.CHARGE);
            if (existingCount > 0) {
                Choice choice = new ChoiceImpl();
                choice.setMessage("Select number of charge counters to remove:");
                for (Integer i = 0; i <= existingCount; i++) {
                    choice.getChoices().add(i.toString());
                }
                if (player.choose(Outcome.Detriment, choice, game)) {
                    permanent.removeCounters(CounterType.CHARGE.getName(), Integer.parseInt(choice.getChoice()), source, game);
                    return true;
                }
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public GremlinMineEffect copy() {
        return new GremlinMineEffect(this);
    }
}
