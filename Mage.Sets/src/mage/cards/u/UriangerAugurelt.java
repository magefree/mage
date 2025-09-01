package mage.cards.u;

import mage.MageIdentifier;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.PlayLandOrCastSpellTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.asthought.MayLookAtTargetCardEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UriangerAugurelt extends CardImpl {

    public UriangerAugurelt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever you play a land from exile or cast a spell from exile, you gain 2 life.
        this.addAbility(new PlayLandOrCastSpellTriggeredAbility(new GainLifeEffect(2), true, false));

        // Draw Arcanum -- {T}: Look at the top card of your library. You may exile it face down.
        this.addAbility(new SimpleActivatedAbility(
                new UriangerAugureltExileEffect(), new TapSourceCost()
        ).withFlavorWord("Draw Arcanum"));

        // Play Arcanum -- {T}: Until end of turn, you may play cards exiled with Urianger Augurelt. Spells you cast this way cost {2} less to cast.
        this.addAbility(new SimpleActivatedAbility(
                new UriangerAugureltPlayEffect(), new TapSourceCost()
        ).setIdentifier(MageIdentifier.UriangerAugureltAlternateCast).withFlavorWord("Play Arcanum"));
    }

    private UriangerAugurelt(final UriangerAugurelt card) {
        super(card);
    }

    @Override
    public UriangerAugurelt copy() {
        return new UriangerAugurelt(this);
    }
}

class UriangerAugureltExileEffect extends OneShotEffect {

    UriangerAugureltExileEffect() {
        super(Outcome.Benefit);
        staticText = "look at the top card of your library. You may exile it face down";
    }

    private UriangerAugureltExileEffect(final UriangerAugureltExileEffect effect) {
        super(effect);
    }

    @Override
    public UriangerAugureltExileEffect copy() {
        return new UriangerAugureltExileEffect(this);
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
        player.lookAtCards("Top card of library", card, game);
        if (!player.chooseUse(Outcome.DrawCard, "Exile " + card.getLogName() + " face down?", source, game)) {
            return false;
        }
        player.moveCardsToExile(
                card, source, game, false,
                CardUtil.getExileZoneId(game, source),
                CardUtil.getSourceName(game, source)
        );
        card.setFaceDown(true, game);
        game.addEffect(new MayLookAtTargetCardEffect(source.getControllerId())
                .setTargetPointer(new FixedTarget(card, game)), source);
        return true;
    }
}

class UriangerAugureltPlayEffect extends AsThoughEffectImpl {

    UriangerAugureltPlayEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE,
                Duration.EndOfTurn, Outcome.AIDontUseIt);
        staticText = "until end of turn, you may play cards exiled " +
                "with {this}. Spells you cast this way cost {2} less to cast";
    }

    private UriangerAugureltPlayEffect(final UriangerAugureltPlayEffect effect) {
        super(effect);
    }

    @Override
    public UriangerAugureltPlayEffect copy() {
        return new UriangerAugureltPlayEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (!source.isControlledBy(affectedControllerId)) {
            return false;
        }
        Card card = game.getCard(objectId);
        if (card == null || !Optional
                .ofNullable(CardUtil.getExileZoneId(game, source))
                .map(game.getState().getExile()::getExileZone)
                .filter(e -> e.contains(objectId))
                .isPresent()) {
            return false;
        }
        if (card.isLand(game)) {
            return true;
        }
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            ManaCosts<ManaCost> newCost = CardUtil.reduceCost(card.getManaCost(), 2);
            if (newCost.isEmpty()) {
                newCost.add(new GenericManaCost(0));
            }
            controller.setCastSourceIdWithAlternateMana(
                    card.getId(), newCost, null, MageIdentifier.UriangerAugureltAlternateCast
            );
        }
        return true;
    }
}
