package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.Set;
import java.util.UUID;

/**
 *
 * @author weirddan455
 */
public final class FireGiantsFury extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent(SubType.GIANT);

    public FireGiantsFury(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}");

        // Target Giant you control gets +2/+2 and gains trample until end of turn.
        Effect effect = new BoostTargetEffect(2, 2, Duration.EndOfTurn);
        effect.setText("Target Giant you control gets +2/+2");
        this.getSpellAbility().addEffect(effect);

        effect = new GainAbilityTargetEffect(TrampleAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and gains trample until end of turn");
        this.getSpellAbility().addEffect(effect);

        // Whenever it deals combat damage to a player this turn, exile that many cards from the top of your library. Until the end of your next turn, you may play those cards.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(new FireGiantsFuryEffect(), false, true);
        effect = new GainAbilityTargetEffect(ability, Duration.EndOfTurn);
        effect.setText("Whenever it deals combat damage to a player this turn, exile that many cards from the top of your library. Until the end of your next turn, you may play those cards");
        this.getSpellAbility().addEffect(effect);

        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private FireGiantsFury(final FireGiantsFury card) {
        super(card);
    }

    @Override
    public FireGiantsFury copy() {
        return new FireGiantsFury(this);
    }
}

class FireGiantsFuryEffect extends OneShotEffect {

    FireGiantsFuryEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "exile that many cards from the top of your library. Until the end of your next turn, you may play those cards";
    }

    private FireGiantsFuryEffect(final FireGiantsFuryEffect effect) {
        super(effect);
    }

    @Override
    public FireGiantsFuryEffect copy() {
        return new FireGiantsFuryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Integer damage = (Integer) getValue("damage");
        if (controller != null && damage != null) {
            Set<Card> cards = controller.getLibrary().getTopCards(game, damage);
            Card sourceCard = game.getCard(source.getSourceId());
            controller.moveCardsToExile(cards, source, game, true, CardUtil.getCardExileZoneId(game, source), sourceCard != null ? sourceCard.getIdName() : "");

            for (Card card : cards) {
                ContinuousEffect effect = new FireGiantsFuryMayPlayEffect();
                effect.setTargetPointer(new FixedTarget(card.getId()));
                game.addEffect(effect, source);
            }

            return true;
        }
        return false;
    }
}

class FireGiantsFuryMayPlayEffect extends AsThoughEffectImpl {

    private int castOnTurn = 0;

    FireGiantsFuryMayPlayEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.Custom, Outcome.Benefit);
        this.staticText = "Until the end of your next turn, you may play that card.";
    }

    private FireGiantsFuryMayPlayEffect(final FireGiantsFuryMayPlayEffect effect) {
        super(effect);
        castOnTurn = effect.castOnTurn;
    }

    @Override
    public FireGiantsFuryMayPlayEffect copy() {
        return new FireGiantsFuryMayPlayEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        castOnTurn = game.getTurnNum();
    }

    @Override
    public boolean isInactive(Ability source, Game game) {
        if (castOnTurn != game.getTurnNum() && game.getPhase().getStep().getType() == PhaseStep.END_TURN) {
            return game.isActivePlayer(source.getControllerId());
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        UUID objectIdToCast = CardUtil.getMainCardId(game, sourceId);
        return source.isControlledBy(affectedControllerId)
                && getTargetPointer().getTargets(game, source).contains(objectIdToCast);
    }
}
