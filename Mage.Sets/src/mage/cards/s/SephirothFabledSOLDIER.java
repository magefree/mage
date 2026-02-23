package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.*;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.command.emblems.SephirothOneWingedAngelEmblem;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.target.common.TargetSacrifice;
import mage.watchers.common.AbilityResolvedWatcher;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SephirothFabledSOLDIER extends TransformingDoubleFacedCard {

    public SephirothFabledSOLDIER(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.AVATAR, SubType.SOLDIER}, "{2}{B}",
                "Sephiroth, One-Winged Angel",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.ANGEL, SubType.NIGHTMARE, SubType.AVATAR}, "B"
        );

        // Sephiroth, Fabled SOLDIER
        this.getLeftHalfCard().setPT(3, 3);

        // Whenever Sephiroth enters or attacks, you may sacrifice another creature. If you do, draw a card.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(new DoIfCostPaid(
                new DrawCardSourceControllerEffect(1),
                new SacrificeTargetCost(StaticFilters.FILTER_ANOTHER_CREATURE)
        )));

        // Whenever another creature dies, target opponent loses 1 life and you gain 1 life. If this is the fourth time this ability has resolved this turn, transform Sephiroth.
        Ability ability = new DiesCreatureTriggeredAbility(
                new LoseLifeTargetEffect(1), false,
                StaticFilters.FILTER_ANOTHER_CREATURE
        );
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
        ability.addEffect(new IfAbilityHasResolvedXTimesEffect(4, new TransformSourceEffect()));
        ability.addTarget(new TargetOpponent());
        ability.addWatcher(new AbilityResolvedWatcher());
        this.getLeftHalfCard().addAbility(ability);

        // Sephiroth, One-Winged Angel
        this.getRightHalfCard().setPT(5, 5);

        // Flying
        this.getRightHalfCard().addAbility(FlyingAbility.getInstance());

        // Super Nova -- As this creature transforms into Sephiroth, One-Winged Angel, you get an emblem with "Whenever a creature dies, target opponent loses 1 life and you gain 1 life."
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new SephirothOneWingedAngelEmblemEffect()).withFlavorWord("Super Nova"));

        // Whenever Sephiroth attacks, you may sacrifice any number of other creatures. If you do, draw that many cards.
        this.getRightHalfCard().addAbility(new AttacksTriggeredAbility(new SephirothOneWingedAngelSacrificeEffect()));

    }

    private SephirothFabledSOLDIER(final SephirothFabledSOLDIER card) {
        super(card);
    }

    @Override
    public SephirothFabledSOLDIER copy() {
        return new SephirothFabledSOLDIER(this);
    }
}

class SephirothOneWingedAngelEmblemEffect extends ReplacementEffectImpl {

    SephirothOneWingedAngelEmblemEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "as this creature transforms into {this}, " +
                "you get an emblem with \"Whenever a creature dies, target opponent loses 1 life and you gain 1 life.\"";
    }

    private SephirothOneWingedAngelEmblemEffect(final SephirothOneWingedAngelEmblemEffect effect) {
        super(effect);
    }

    @Override
    public SephirothOneWingedAngelEmblemEffect copy() {
        return new SephirothOneWingedAngelEmblemEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Optional.ofNullable(source.getSourceObject(game))
                .ifPresent(obj -> game.addEmblem(new SephirothOneWingedAngelEmblem(), obj, source));
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TRANSFORMING;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return source.getSourceId().equals(event.getTargetId())
                && source.getSourcePermanentIfItStillExists(game) != null;
    }
}

class SephirothOneWingedAngelSacrificeEffect extends OneShotEffect {

    SephirothOneWingedAngelSacrificeEffect() {
        super(Outcome.Benefit);
        staticText = "you may sacrifice any number of other creatures. If you do, draw that many cards";
    }

    private SephirothOneWingedAngelSacrificeEffect(final SephirothOneWingedAngelSacrificeEffect effect) {
        super(effect);
    }

    @Override
    public SephirothOneWingedAngelSacrificeEffect copy() {
        return new SephirothOneWingedAngelSacrificeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetSacrifice target = new TargetSacrifice(
                0, Integer.MAX_VALUE, StaticFilters.FILTER_ANOTHER_CREATURE
        );
        player.choose(outcome, target, source, game);
        int count = 0;
        for (UUID targetId : target.getTargets()) {
            Permanent permanent = game.getPermanent(targetId);
            if (permanent != null && permanent.sacrifice(source, game)) {
                count++;
            }
        }
        if (count < 1) {
            return false;
        }
        player.drawCards(count, source, game);
        return true;
    }
}
