package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SummonKujata extends CardImpl {

    public SummonKujata(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{5}{R}");

        this.subtype.add(SubType.SAGA);
        this.subtype.add(SubType.OX);
        this.power = new MageInt(7);
        this.toughness = new MageInt(5);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I - Lightning -- This creature deals 3 damage to each of up to two target creatures.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, ability -> {
            ability.addEffect(new DamageTargetEffect(3));
            ability.addTarget(new TargetCreaturePermanent(0, 2));
            ability.withFlavorWord("Lightning");
        });

        // II - Ice -- Up to three target creatures can't block this turn.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_II, ability -> {
            ability.addEffect(new CantBlockTargetEffect(Duration.EndOfTurn));
            ability.addTarget(new TargetCreaturePermanent(0, 3));
            ability.withFlavorWord("Ice");
        });

        // III - Fire -- Discard a card, then draw two cards. When you discard a card this way, this creature deals damage equal to that card's mana value to each opponent.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, ability -> {
            ability.addEffect(new SummonKujataEffect());
            ability.withFlavorWord("Fire");
        });
        this.addAbility(sagaAbility);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());
    }

    private SummonKujata(final SummonKujata card) {
        super(card);
    }

    @Override
    public SummonKujata copy() {
        return new SummonKujata(this);
    }
}

class SummonKujataEffect extends OneShotEffect {

    SummonKujataEffect() {
        super(Outcome.Benefit);
        staticText = "discard a card, then draw two cards. When you discard a card this way, " +
                "this creature deals damage equal to that card's mana value to each opponent";
    }

    private SummonKujataEffect(final SummonKujataEffect effect) {
        super(effect);
    }

    @Override
    public SummonKujataEffect copy() {
        return new SummonKujataEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Card card = player.discard(1, false, false, source, game).getRandom(game);
        player.drawCards(2, source, game);
        if (card != null) {
            game.fireReflexiveTriggeredAbility(new ReflexiveTriggeredAbility(
                    new DamagePlayersEffect(card.getManaValue(), TargetController.OPPONENT), false
            ), source);
        }
        return true;
    }
}
