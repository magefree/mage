package mage.cards.m;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.token.BallisticBoulder;
import mage.game.permanent.token.PhyrexianHorrorRedToken;
import mage.game.permanent.token.Token;
import mage.target.targetpointer.FixedTargets;

/**
 *
 * @author Susucr
 */
public final class MordorTrebuchet extends CardImpl {

    private static final FilterCreaturePermanent filter
        = new FilterCreaturePermanent("Goblins and/or Orcs");

    static {
        filter.add(Predicates.or(
            SubType.GOBLIN.getPredicate(),
            SubType.ORC.getPredicate()
        ));
    }

    public MordorTrebuchet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{B}");
        
        this.subtype.add(SubType.WALL);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // Whenever you attack with one or more Goblins and/or Orcs,
        // create a 2/1 colorless Construct artifact creature token
        // with flying named Ballistic Boulder that's tapped and attacking.
        // Sacrifice that token at end of combat.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(
            new MordorTrebuchetEffect(), 1, filter
        ).setTriggerPhrase("Whenever you attack with one or more Goblins and/or Orcs, "));
    }

    private MordorTrebuchet(final MordorTrebuchet card) {
        super(card);
    }

    @Override
    public MordorTrebuchet copy() {
        return new MordorTrebuchet(this);
    }
}

class MordorTrebuchetEffect extends OneShotEffect {

    MordorTrebuchetEffect() {
        super(Outcome.Benefit);
        staticText = "create a 2/1 colorless Construct artifact creature token " +
            "with flying named Ballistic Boulder that's tapped and attacking. " +
            "Sacrifice that token at end of combat.";
    }

    private MordorTrebuchetEffect(final MordorTrebuchetEffect effect) {
        super(effect);
    }

    @Override
    public MordorTrebuchetEffect copy() {
        return new MordorTrebuchetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Token token = new BallisticBoulder();
        token.putOntoBattlefield(1, game, source, source.getControllerId(), true, true);
        game.addDelayedTriggeredAbility(new AtTheEndOfCombatDelayedTriggeredAbility(
            new SacrificeTargetEffect().setText("sacrifice it")
                .setTargetPointer(new FixedTargets(token, game))
        ), source);
        return true;
    }
}
