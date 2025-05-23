package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.command.emblems.SephirothOneWingedAngelEmblem;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetSacrifice;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SephirothOneWingedAngel extends CardImpl {

    public SephirothOneWingedAngel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ANGEL);
        this.subtype.add(SubType.NIGHTMARE);
        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);
        this.nightCard = true;
        this.color.setBlack(true);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Super Nova -- As this creature transforms into Sephiroth, One-Winged Angel, you get an emblem with "Whenever a creature dies, target opponent loses 1 life and you gain 1 life."
        this.addAbility(new SimpleStaticAbility(new SephirothOneWingedAngelEmblemEffect()).withFlavorWord("Super Nova"));

        // Whenever Sephiroth attacks, you may sacrifice any number of other creatures. If you do, draw that many cards.
        this.addAbility(new AttacksTriggeredAbility(new SephirothOneWingedAngelSacrificeEffect()));
    }

    private SephirothOneWingedAngel(final SephirothOneWingedAngel card) {
        super(card);
    }

    @Override
    public SephirothOneWingedAngel copy() {
        return new SephirothOneWingedAngel(this);
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
