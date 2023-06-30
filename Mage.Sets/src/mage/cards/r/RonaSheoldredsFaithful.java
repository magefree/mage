package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RonaSheoldredsFaithful extends CardImpl {

    public RonaSheoldredsFaithful(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Whenever you cast an instant or sorcery spell, each opponent loses 1 life.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new LoseLifeOpponentsEffect(1),
                StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY, false
        ));

        // You may cast Rona, Sheoldred's Faithful from your graveyard by discarding two cards in addition to paying its other costs.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new RonaSheoldredsFaithfulEffect()));
    }

    private RonaSheoldredsFaithful(final RonaSheoldredsFaithful card) {
        super(card);
    }

    @Override
    public RonaSheoldredsFaithful copy() {
        return new RonaSheoldredsFaithful(this);
    }
}

class RonaSheoldredsFaithfulEffect extends AsThoughEffectImpl {

    public RonaSheoldredsFaithfulEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfGame, Outcome.Benefit);
        this.staticText = "you may cast {this} from your graveyard " +
                "by discarding two cards in addition to paying its other costs";
    }

    private RonaSheoldredsFaithfulEffect(final RonaSheoldredsFaithfulEffect effect) {
        super(effect);
    }

    @Override
    public RonaSheoldredsFaithfulEffect copy() {
        return new RonaSheoldredsFaithfulEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (!source.getSourceId().equals(objectId)
                || !source.isControlledBy(affectedControllerId)
                || game.getState().getZone(objectId) != Zone.GRAVEYARD) {
            return false;
        }
        Player controller = game.getPlayer(affectedControllerId);
        if (controller == null) {
            return false;
        }
        Costs<Cost> costs = new CostsImpl<>();
        costs.add(new DiscardTargetCost(new TargetCardInHand(2, StaticFilters.FILTER_CARD_CARDS)));
        controller.setCastSourceIdWithAlternateMana(objectId, new ManaCostsImpl<>("{1}{U}{B}{B}"), costs);
        return true;
    }
}
