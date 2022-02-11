package mage.cards.t;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.continuous.CastAsThoughItHadFlashAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TeferiTimeRaveler extends CardImpl {

    private static final FilterCard filter = new FilterCard("sorcery spells");
    private static final FilterPermanent filter2 = new FilterPermanent("artifact, creature, or enchantment");

    static {
        filter.add(CardType.SORCERY.getPredicate());
        filter2.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate(),
                CardType.ENCHANTMENT.getPredicate()
        ));
    }

    public TeferiTimeRaveler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{1}{W}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.TEFERI);
        this.setStartingLoyalty(4);

        // Each opponent can cast spells only any time they could cast a sorcery.
        this.addAbility(new SimpleStaticAbility(new TeferiTimeRavelerReplacementEffect()));

        // +1: Until your next turn, you may cast sorcery spells as though they had flash.
        this.addAbility(new LoyaltyAbility(new CastAsThoughItHadFlashAllEffect(
                Duration.UntilYourNextTurn, filter
        ).setText("Until your next turn, you may cast sorcery spells as though they had flash"), 1));

        // -3: Return up to one target artifact, creature, or enchantment to its owner's hand. Draw a card.
        Ability ability = new LoyaltyAbility(new ReturnToHandTargetEffect(), -3);
        ability.addEffect(new DrawCardSourceControllerEffect(1).setText("Draw a card"));
        ability.addTarget(new TargetPermanent(0, 1, filter2, false));
        this.addAbility(ability);
    }

    private TeferiTimeRaveler(final TeferiTimeRaveler card) {
        super(card);
    }

    @Override
    public TeferiTimeRaveler copy() {
        return new TeferiTimeRaveler(this);
    }
}

class TeferiTimeRavelerReplacementEffect extends ContinuousRuleModifyingEffectImpl {

    TeferiTimeRavelerReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "Each opponent can cast spells only any time they could cast a sorcery";
    }

    private TeferiTimeRavelerReplacementEffect(final TeferiTimeRavelerReplacementEffect effect) {
        super(effect);
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject mageObject = game.getObject(source.getSourceId());
        if (mageObject != null) {
            return "You can cast spells only any time you could cast a sorcery  (" + mageObject.getIdName() + ").";
        }
        return null;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && controller.hasOpponent(event.getPlayerId(), game)) {
            return !game.canPlaySorcery(event.getPlayerId());
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public TeferiTimeRavelerReplacementEffect copy() {
        return new TeferiTimeRavelerReplacementEffect(this);
    }
}
