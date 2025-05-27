package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.condition.common.CastFromGraveyardSourceCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.counters.Counters;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EsperOrigins extends CardImpl {

    public EsperOrigins(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");
        this.secondSideCardClazz = mage.cards.s.SummonEsperMaduin.class;

        // Surveil 2. You gain 2 life. If this spell was cast from a graveyard, exile it, then put it onto the battlefield transformed under its owner's control with a finality counter on it.
        this.getSpellAbility().addEffect(new SurveilEffect(2, false));
        this.getSpellAbility().addEffect(new GainLifeEffect(2));
        this.getSpellAbility().addEffect(new EsperOriginsEffect());
        this.addAbility(new TransformAbility());

        // Flashback {3}{G}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{3}{G}")));
    }

    private EsperOrigins(final EsperOrigins card) {
        super(card);
    }

    @Override
    public EsperOrigins copy() {
        return new EsperOrigins(this);
    }
}

class EsperOriginsEffect extends OneShotEffect {

    EsperOriginsEffect() {
        super(Outcome.Benefit);
        staticText = "If this spell was cast from a graveyard, exile it, then put it onto the battlefield " +
                "transformed under its owner's control with a finality counter on it.";
    }

    private EsperOriginsEffect(final EsperOriginsEffect effect) {
        super(effect);
    }

    @Override
    public EsperOriginsEffect copy() {
        return new EsperOriginsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (!CastFromGraveyardSourceCondition.instance.apply(game, source)) {
            return false;
        }
        Player player = game.getPlayer(source.getControllerId());
        Spell spell = game.getSpell(source.getId());
        if (player == null || spell == null) {
            return false;
        }
        Card card = spell.getMainCard();
        player.moveCards(card, Zone.EXILED, source, game);
        game.setEnterWithCounters(card.getId(), new Counters(CounterType.FINALITY.createInstance()));
        game.getState().setValue(TransformAbility.VALUE_KEY_ENTER_TRANSFORMED + card.getId(), Boolean.TRUE);
        player.moveCards(
                card, Zone.BATTLEFIELD, source, game, false,
                false, true, null
        );
        return true;
    }
}
