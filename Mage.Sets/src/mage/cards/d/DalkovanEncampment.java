package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedUnlessAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.common.delayed.WhenYouAttackDelayedTriggeredAbility;
import mage.abilities.condition.common.YouControlPermanentCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.token.RedWarriorToken;
import mage.game.permanent.token.Token;
import mage.target.targetpointer.FixedTargets;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DalkovanEncampment extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("a Swamp or a Mountain");

    static {
        filter.add(Predicates.or(
                SubType.SWAMP.getPredicate(),
                SubType.MOUNTAIN.getPredicate()
        ));
    }

    private static final YouControlPermanentCondition condition = new YouControlPermanentCondition(filter);

    public DalkovanEncampment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // This land enters tapped unless you control a Swamp or a Mountain.
        this.addAbility(new EntersBattlefieldTappedUnlessAbility(condition).addHint(condition.getHint()));

        // {T}: Add {W}.
        this.addAbility(new WhiteManaAbility());

        // {2}{W}, {T}: Whenever you attack this turn, create two 1/1 red Warrior creature tokens that are tapped and attacking. Sacrifice them at the beginning of the next end step.
        Ability ability = new SimpleActivatedAbility(new CreateDelayedTriggeredAbilityEffect(
                new WhenYouAttackDelayedTriggeredAbility(new DalkovanEncampmentEffect())
        ), new ManaCostsImpl<>("{2}{W}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private DalkovanEncampment(final DalkovanEncampment card) {
        super(card);
    }

    @Override
    public DalkovanEncampment copy() {
        return new DalkovanEncampment(this);
    }
}

class DalkovanEncampmentEffect extends OneShotEffect {

    DalkovanEncampmentEffect() {
        super(Outcome.Benefit);
        staticText = "create two 1/1 red Warrior creature tokens that are tapped and attacking. " +
                "Sacrifice them at the beginning of the next end step";
    }

    private DalkovanEncampmentEffect(final DalkovanEncampmentEffect effect) {
        super(effect);
    }

    @Override
    public DalkovanEncampmentEffect copy() {
        return new DalkovanEncampmentEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Token token = new RedWarriorToken();
        token.putOntoBattlefield(2, game, source, source.getControllerId(), true, true);
        game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(
                new SacrificeTargetEffect("sacrifice those tokens")
                        .setTargetPointer(new FixedTargets(token, game))
        ), source);
        return true;
    }
}
