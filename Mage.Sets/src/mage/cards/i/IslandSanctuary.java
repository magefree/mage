
package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.combat.CantAttackYouAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.IslandwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class IslandSanctuary extends CardImpl {

    public IslandSanctuary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{W}");

        // If you would draw a card during your draw step, instead you may skip that draw. If you do, until your next turn, you can't be attacked except by creatures with flying and/or islandwalk.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new IslandSanctuaryEffect()));
    }

    private IslandSanctuary(final IslandSanctuary card) {
        super(card);
    }

    @Override
    public IslandSanctuary copy() {
        return new IslandSanctuary(this);
    }
}

class IslandSanctuaryEffect extends ReplacementEffectImpl {

    private static final FilterCreaturePermanent notFlyingorIslandwalkCreatures = new FilterCreaturePermanent("except by creatures with flying and/or islandwalk");

    static {
        notFlyingorIslandwalkCreatures.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
        notFlyingorIslandwalkCreatures.add(Predicates.not(new AbilityPredicate(IslandwalkAbility.class)));
    }

    IslandSanctuaryEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If you would draw a card during your draw step, instead you may skip that draw. If you do, until your next turn, you can't be attacked except by creatures with flying and/or islandwalk";
    }

    IslandSanctuaryEffect(final IslandSanctuaryEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(event.getPlayerId());
        if (controller != null && controller.chooseUse(outcome, "Skip draw card? (If you do, until your next turn, you can't be attacked except by creatures with flying and/or islandwalk)", source, game)) {
            game.informPlayers(controller.getLogName() + " skips their draw card action. Until their next turn, they can't be attacked except by creatures with flying and/or islandwalk");
            game.addEffect(new CantAttackYouAllEffect(Duration.UntilYourNextTurn, notFlyingorIslandwalkCreatures), source);
            return true;
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DRAW_CARD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return source.isControlledBy(event.getPlayerId()) && game.getTurnStepType() == PhaseStep.DRAW && game.getActivePlayerId().equals(source.getControllerId());
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public IslandSanctuaryEffect copy() {
        return new IslandSanctuaryEffect(this);
    }
}
