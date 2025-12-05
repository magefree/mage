package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.mana.RedManaAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;
import mage.util.CardUtil;
import mage.watchers.common.CastSpellLastTurnWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VancesBlastingCannons extends TransformingDoubleFacedCard {

    public VancesBlastingCannons(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ENCHANTMENT}, new SubType[]{}, "{3}{R}",
                "Spitfire Bastion",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.LAND}, new SubType[]{}, ""
        );

        // Vance's Blasting Cannons
        // At the beginning of your upkeep, exile the top card of your library.  If it's a nonland card, you may cast that card this turn.
        this.getLeftHalfCard().addAbility(new BeginningOfUpkeepTriggeredAbility(new VancesBlastingCannonsExileEffect()));

        // Whenever you cast your third spell in a turn, transform Vance's Blasting Cannons.
        this.getLeftHalfCard().addAbility(new VancesBlastingCannonsTriggeredAbility());

        // Spitfire Bastion
        // {T}: Add {R}.
        this.getRightHalfCard().addAbility(new RedManaAbility());

        // {2}{R}, {T}: Spitfire Bastion deals 3 damage to any target.
        Ability ability = new SimpleActivatedAbility(new DamageTargetEffect(3), new TapSourceCost());
        ability.addCost(new ManaCostsImpl<>("{2}{R}"));
        ability.addTarget(new TargetAnyTarget());
        this.getRightHalfCard().addAbility(ability);
    }

    private VancesBlastingCannons(final VancesBlastingCannons card) {
        super(card);
    }

    @Override
    public VancesBlastingCannons copy() {
        return new VancesBlastingCannons(this);
    }
}

class VancesBlastingCannonsExileEffect extends OneShotEffect {

    VancesBlastingCannonsExileEffect() {
        super(Outcome.Benefit);
        this.staticText = "exile the top card of your library. If it's a nonland card, you may cast that card this turn";
    }

    private VancesBlastingCannonsExileEffect(final VancesBlastingCannonsExileEffect effect) {
        super(effect);
    }

    @Override
    public VancesBlastingCannonsExileEffect copy() {
        return new VancesBlastingCannonsExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Card card = player.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }
        player.moveCards(card, Zone.EXILED, source, game);
        if (!card.isLand(game)) {
            CardUtil.makeCardPlayable(game, source, card, true, Duration.EndOfTurn, false);
        }
        return true;
    }
}

class VancesBlastingCannonsTriggeredAbility extends TriggeredAbilityImpl {

    public VancesBlastingCannonsTriggeredAbility() {
        super(Zone.BATTLEFIELD, new TransformSourceEffect(), true);
        setTriggerPhrase("Whenever you cast your third spell in a turn, ");
    }

    private VancesBlastingCannonsTriggeredAbility(final VancesBlastingCannonsTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public VancesBlastingCannonsTriggeredAbility copy() {
        return new VancesBlastingCannonsTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return isControlledBy(event.getPlayerId())
                && game
                .getState()
                .getWatcher(CastSpellLastTurnWatcher.class)
                .getAmountOfSpellsPlayerCastOnCurrentTurn(event.getPlayerId()) == 3;
    }
}
