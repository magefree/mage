package mage.cards.b;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.OpponentsLostLifeCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.*;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class BloodsoakedInsight extends ModalDoubleFacedCard {

    private static final DynamicValue xValue = OpponentsLostLifeCount.instance;
    private static final Hint hint = new ValueHint("life opponents lost this turn", xValue);

    public BloodsoakedInsight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.SORCERY}, new SubType[]{}, "{5}{B/R}{B/R}",
                "Sanguine Morass", new CardType[]{CardType.LAND}, new SubType[]{}, ""
        );

        // 1.
        // Bloodsoaked Insight
        // Sorcery

        // This spell costs {1} less to cast for each 1 life your opponents have lost this turn.
        this.getLeftHalfCard().addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionSourceEffect(xValue)
                .setText("this spell costs {1} less to cast for each 1 life your opponents have lost this turn")
        ).addHint(hint).setRuleAtTheTop(true));

        // Target opponent exiles the top three cards of their library. Until the end of your next turn, you may play those cards. If you cast a spell this way, mana of any type can be spent to cast it.
        this.getLeftHalfCard().getSpellAbility().addEffect(new BloodsoakedInsightTargetEffect());
        this.getLeftHalfCard().getSpellAbility().addTarget(new TargetOpponent());

        // 2.
        // Sanguine Morass
        // Land

        // Sanguine Morass enters the battlefield tapped.
        this.getRightHalfCard().addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {B} or {R}.
        this.getRightHalfCard().addAbility(new BlackManaAbility());
        this.getRightHalfCard().addAbility(new RedManaAbility());
    }

    private BloodsoakedInsight(final BloodsoakedInsight card) {
        super(card);
    }

    @Override
    public BloodsoakedInsight copy() {
        return new BloodsoakedInsight(this);
    }
}

class BloodsoakedInsightTargetEffect extends OneShotEffect {

    BloodsoakedInsightTargetEffect() {
        super(Outcome.Detriment);
        staticText = "Target opponent exiles the top three cards of their library. "
                + "Until the end of your next turn, you may play those cards. "
                + "If you cast a spell this way, mana of any type can be spent to cast it.";
    }

    private BloodsoakedInsightTargetEffect(final BloodsoakedInsightTargetEffect effect) {
        super(effect);
    }

    @Override
    public BloodsoakedInsightTargetEffect copy() {
        return new BloodsoakedInsightTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (controller == null || opponent == null) {
            return false;
        }
        Cards cards = new CardsImpl(opponent.getLibrary().getTopCards(game, 3));
        if (cards.isEmpty()) {
            return false;
        }
        UUID exileId = CardUtil.getExileZoneId(game, source);
        MageObject sourceObject = source.getSourceObject(game);
        String exileName = sourceObject == null ? null : sourceObject.getIdName();
        controller.moveCardsToExile(cards.getCards(game), source, game, true, exileId, exileName);
        cards.retainZone(Zone.EXILED, game);
        for (Card card : cards.getCards(game)) {
            CardUtil.makeCardPlayable(game, source, card, false, Duration.UntilEndOfYourNextTurn, true);
        }
        return true;
    }
}
