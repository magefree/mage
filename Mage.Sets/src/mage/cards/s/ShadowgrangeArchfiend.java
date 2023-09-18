package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.MadnessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Alex-Vasile
 */
public final class ShadowgrangeArchfiend extends CardImpl {
    public ShadowgrangeArchfiend(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{B}");
        this.subtype.add(SubType.DEMON);

        this.power = new MageInt(8);
        this.toughness = new MageInt(4);

        // When Shadowgrange Archfiend enters the battlefield,
        // each opponent sacrifices a creature with the greatest power among creatures they control.
        // You gain life equal to the greatest power among creatures sacrificed this way.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ShadowgrangeArchfiendEffect()));

        // Madness—{2}{B}, Pay 8 life.
        MadnessAbility madnessAbility = new MadnessAbility(new ManaCostsImpl<>("{2}{B}"), 8);
        this.addAbility(madnessAbility);
    }

    private ShadowgrangeArchfiend(final ShadowgrangeArchfiend card) { super(card); }

    @Override
    public ShadowgrangeArchfiend copy() { return new ShadowgrangeArchfiend(this); }
}

class ShadowgrangeArchfiendEffect extends OneShotEffect {

    public ShadowgrangeArchfiendEffect() {
        super(Outcome.Benefit);
        this.staticText = "each opponent sacrifices a creature with the greatest power among creatures they control. " +
                "You gain life equal to the greatest power among creatures sacrificed this way";
    }

    private ShadowgrangeArchfiendEffect(final ShadowgrangeArchfiendEffect effect) { super(effect); }

    @Override
    public ShadowgrangeArchfiendEffect copy() { return new ShadowgrangeArchfiendEffect(this); }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {return false; }

        List<Permanent> toSacrifice = new ArrayList<>();

        // Iterate through each opponent
        for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
            if (!controller.hasOpponent(playerId, game)) { continue; }

            Player opponent = game.getPlayer(playerId);
            if (opponent == null) { continue; }

            int greatestPower = Integer.MIN_VALUE;
            int numberOfCreatures = 0;
            Permanent creatureToSacrifice = null;

            // Iterature through each creature
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, playerId, game)) {
                if (permanent.getPower().getValue() > greatestPower) {
                    greatestPower = permanent.getPower().getValue();
                    numberOfCreatures = 1;
                    creatureToSacrifice = permanent;
                } else if (permanent.getPower().getValue() == greatestPower) {
                    numberOfCreatures++;
                }
            }

            // If multiple creatures are tied for having the greatest power
            if (numberOfCreatures > 1) {
                FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent(
                        "creature to sacrifice with power equal to " + greatestPower);
                filter.add(new PowerPredicate(ComparisonType.EQUAL_TO, greatestPower));
                Target target = new TargetControlledCreaturePermanent(filter);
                if (opponent.choose(outcome, target, source, game)) {
                    creatureToSacrifice = game.getPermanent(target.getFirstTarget());
                }
            }

            if (creatureToSacrifice != null) {
                toSacrifice.add(creatureToSacrifice);
            }
        }

        int greatestPowerAmongAllCreaturesSacked = Integer.MIN_VALUE;
        int powerOfCurrentCreature;

        // Sack the creatures and save the greaterest power amoung those which were sacked
        for (Permanent permanent : toSacrifice) {
            powerOfCurrentCreature = permanent.getPower().getValue();

            // Try to sack it
            if (permanent.sacrifice(source, game)) {
                if (powerOfCurrentCreature > greatestPowerAmongAllCreaturesSacked) {
                    greatestPowerAmongAllCreaturesSacked = powerOfCurrentCreature;
                }
            }
        }

        // Gain life equal to the power of greatest creature sacked, if it is positive
        if (greatestPowerAmongAllCreaturesSacked > 0) {
            new GainLifeEffect(greatestPowerAmongAllCreaturesSacked).apply(game, source);
        }

        return true;
    }
}
