
package mage.cards.s;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import static mage.filter.StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author Styxo
 */
public final class SithEvoker extends CardImpl {

    public SithEvoker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.subtype.add(SubType.CHISS);
        this.subtype.add(SubType.SITH);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {T}, {B}, Sacrifice a creature: You gain life equal to that creature's power or toughness.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new SithEvokerEffect(), new ManaCostsImpl<>("{B}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(FILTER_CONTROLLED_CREATURE_SHORT_TEXT)));

        this.addAbility(ability);
    }

    private SithEvoker(final SithEvoker card) {
        super(card);
    }

    @Override
    public SithEvoker copy() {
        return new SithEvoker(this);
    }
}

class SithEvokerEffect extends OneShotEffect {

    private static final Set<String> choices = new LinkedHashSet<>();

    static {
        choices.add("Gain life equal to creature's power");
        choices.add("Gain life equal to creature's toughness");
    }

    public SithEvokerEffect() {
        super(Outcome.GainLife);
        staticText = "You gain life equal to that creature's power or toughness";
    }

    public SithEvokerEffect(final SithEvokerEffect effect) {
        super(effect);
    }

    @Override
    public SithEvokerEffect copy() {
        return new SithEvokerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Choice choice = new ChoiceImpl(true);
            choice.setMessage("Choose mode");
            choice.setChoices(choices);
            if (!controller.choose(outcome, choice, game)) {
                return false;
            }
            Card sourceCard = game.getCard(source.getSourceId());
            if (sourceCard != null) {
                for (Object cost : source.getCosts()) {
                    if (cost instanceof SacrificeTargetCost) {
                        Permanent p = (Permanent) game.getLastKnownInformation(((SacrificeTargetCost) cost).getPermanents().get(0).getId(), Zone.BATTLEFIELD);
                        if (p != null) {
                            String chosen = choice.getChoice();
                            switch (chosen) {
                                case "Gain life equal to creature's power":
                                    new GainLifeEffect(p.getPower().getValue()).apply(game, source);
                                    break;
                                default: //"Gain life equal to creature's toughness"
                                    new GainLifeEffect(p.getToughness().getValue()).apply(game, source);
                                    break;
                            }
                            return true;
                        }
                    }
                }
            }

        }
        return false;
    }
}
