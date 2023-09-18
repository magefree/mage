package mage.cards.a;

import java.util.Set;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author TheElk801
 */
public final class ApexOfPower extends CardImpl {

    public ApexOfPower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{7}{R}{R}{R}");

        // Exile the top seven cards of your library. Until end of turn, you may cast nonland cards exiled this way.
        this.getSpellAbility().addEffect(new ApexOfPowerSpellEffect());

        // If this spell was cast from your hand, add ten mana of any one color.
        this.getSpellAbility().addEffect(new ApexOfPowerManaEffect().concatBy("<br>"));
    }

    private ApexOfPower(final ApexOfPower card) {
        super(card);
    }

    @Override
    public ApexOfPower copy() {
        return new ApexOfPower(this);
    }
}

class ApexOfPowerSpellEffect extends OneShotEffect {

    public ApexOfPowerSpellEffect() {
        super(Outcome.Benefit);
        this.staticText = "exile the top seven cards of your library. " +
                "Until end of turn, you may cast spells from among them";
    }

    private ApexOfPowerSpellEffect(final ApexOfPowerSpellEffect effect) {
        super(effect);
    }

    @Override
    public ApexOfPowerSpellEffect copy() {
        return new ApexOfPowerSpellEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller == null || sourceObject == null) {
            return false;
        }
        Set<Card> cards = controller.getLibrary().getTopCards(game, 7);
        if (cards.isEmpty()) {
            return false;
        }
        controller.moveCards(cards, Zone.EXILED, source, game);
        for (Card card : cards) {
            if (card.isLand(game)) {
                continue;
            }
            ContinuousEffect effect = new PlayFromNotOwnHandZoneTargetEffect(Zone.EXILED, Duration.EndOfTurn);
            effect.setTargetPointer(new FixedTarget(card, game));
            game.addEffect(effect, source);
        }
        return true;
    }
}

class ApexOfPowerManaEffect extends OneShotEffect {

    public ApexOfPowerManaEffect() {
        super(Outcome.Benefit);
        this.staticText = "If this spell was cast from your hand, add ten mana of any one color.";
    }

    private ApexOfPowerManaEffect(final ApexOfPowerManaEffect effect) {
        super(effect);
    }

    @Override
    public ApexOfPowerManaEffect copy() {
        return new ApexOfPowerManaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getStack().getSpell(source.getSourceId());
        if (spell == null || spell.getFromZone() != Zone.HAND) {
            return false;
        }
        return new AddManaOfAnyColorEffect(10).apply(game, source);
    }
}
