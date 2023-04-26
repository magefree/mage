package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetPlaneswalkerPermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class LilianasTalent extends CardImpl {

    public LilianasTalent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{B}{B}");

        this.subtype.add(SubType.AURA);

        // Enchant planeswalker
        TargetPermanent auraTarget = new TargetPlaneswalkerPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted planeswalker has "[-8]: Put all creature cards from all graveyards onto the battlefield under your control."
        this.addAbility(new SimpleStaticAbility(new GainAbilityAttachedEffect(
                new LoyaltyAbility(new LilianasTalentEffect(), -8),
                AttachmentType.AURA, Duration.WhileOnBattlefield,
                null, "planeswalker"
        )));

        // Whenever a creature deals damage to enchanted planeswalker, destroy that creature.
        this.addAbility(new LilianasTalentTriggeredAbility());
    }

    private LilianasTalent(final LilianasTalent card) {
        super(card);
    }

    @Override
    public LilianasTalent copy() {
        return new LilianasTalent(this);
    }
}

class LilianasTalentEffect extends OneShotEffect {

    LilianasTalentEffect() {
        super(Outcome.Benefit);
        staticText = "put all creature cards from all graveyards onto the battlefield under your control";
    }

    private LilianasTalentEffect(final LilianasTalentEffect effect) {
        super(effect);
    }

    @Override
    public LilianasTalentEffect copy() {
        return new LilianasTalentEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        return player != null
                && player
                .moveCards(
                        game.getState()
                                .getPlayersInRange(source.getControllerId(), game)
                                .stream()
                                .map(game::getPlayer)
                                .map(Player::getGraveyard)
                                .map(gy -> gy.getCards(StaticFilters.FILTER_CARD_CREATURE, game))
                                .flatMap(Collection::stream)
                                .collect(Collectors.toSet()),
                        Zone.BATTLEFIELD, source, game
                );
    }
}

class LilianasTalentTriggeredAbility extends TriggeredAbilityImpl {

    LilianasTalentTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DestroyTargetEffect());
    }

    private LilianasTalentTriggeredAbility(final LilianasTalentTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public LilianasTalentTriggeredAbility copy() {
        return new LilianasTalentTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!Optional
                .of(this.getSourcePermanentIfItStillExists(game))
                .filter(Objects::nonNull)
                .map(Permanent::getAttachedTo)
                .map(event.getTargetId()::equals)
                .orElse(false)) {
            return false;
        }
        Permanent permanent = game.getPermanent(event.getSourceId());
        if (permanent == null || !permanent.isCreature(game)) {
            return false;
        }
        this.getEffects().setTargetPointer(new FixedTarget(permanent, game));
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever a creature deals damage to enchanted planeswalker, destroy that creature.";
    }
}
