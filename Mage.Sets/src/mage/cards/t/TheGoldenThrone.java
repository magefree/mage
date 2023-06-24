package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.mana.AddManaInAnyCombinationEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheGoldenThrone extends CardImpl {

    public TheGoldenThrone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        this.supertype.add(SuperType.LEGENDARY);

        // Arcane Life-support -- If you would lose the game, instead exile The Golden Throne and your life total becomes 1.
        this.addAbility(new SimpleStaticAbility(new TheGoldenThroneEffect()).withFlavorWord("Arcane Life-support"));

        // A Thousand Souls Die Every Day -- {T}, Sacrifice a creature: Add three mana in any combination of colors.
        Ability ability = new SimpleManaAbility(Zone.BATTLEFIELD, new AddManaInAnyCombinationEffect(3), new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT));
        this.addAbility(ability.withFlavorWord("A Thousand Souls Die Every Day"));
    }

    private TheGoldenThrone(final TheGoldenThrone card) {
        super(card);
    }

    @Override
    public TheGoldenThrone copy() {
        return new TheGoldenThrone(this);
    }
}

class TheGoldenThroneEffect extends ReplacementEffectImpl {

    public TheGoldenThroneEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "if you would lose the game, instead exile {this} and your life total becomes 1";
    }

    public TheGoldenThroneEffect(final TheGoldenThroneEffect effect) {
        super(effect);
    }

    @Override
    public TheGoldenThroneEffect copy() {
        return new TheGoldenThroneEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(event.getPlayerId());
        if (player == null) {
            return true;
        }
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent != null) {
            player.moveCards(permanent, Zone.EXILED, source, game);
        }
        player.setLife(1, game, source);
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.LOSES;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return source.isControlledBy(event.getPlayerId());
    }
}