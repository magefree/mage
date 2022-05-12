package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.common.ModeChoiceSourceCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseModeEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.filter.common.FilterArtifactSpell;
import mage.game.Game;
import mage.game.permanent.token.MyrToken;
import mage.players.Player;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MirrodinBesieged extends CardImpl {

    private static final String ruleTrigger1 = "&bull Mirran &mdash; Whenever you cast an artifact spell, " +
            "create a 1/1 colorless Myr artifact creature token.";
    private static final String ruleTrigger2 = "&bull Phyrexian &mdash; At the beginning of your end step, " +
            "draw a card, then discard a card. Then if there are fifteen or more artifact cards in your graveyard, " +
            "target opponent loses the game.";
    private static final FilterSpell filter = new FilterArtifactSpell();

    public MirrodinBesieged(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        // As Mirrodin Besieged enters the battlefield, choose Mirran or Phyrexian.
        this.addAbility(new EntersBattlefieldAbility(
                new ChooseModeEffect("Mirran or Phyrexian?", "Mirran", "Phyrexian"),
                null, "As {this} enters the battlefield, choose Mirran or Phyrexian.", ""
        ));

        // • Mirran — Whenever you cast an artifact spell, create a 1/1 colorless Myr artifact creature token.
        this.addAbility(new ConditionalTriggeredAbility(new SpellCastControllerTriggeredAbility(
                new CreateTokenEffect(new MyrToken()), filter, false
        ), new ModeChoiceSourceCondition("Mirran"), ruleTrigger1));

        // • Phyrexian — At the beginning of your end step, draw a card, then discard a card. Then if there are fifteen or more artifact cards in your graveyard, target opponent loses the game.
        Ability ability = new ConditionalTriggeredAbility(new BeginningOfEndStepTriggeredAbility(
                new MirrodinBesiegedEffect(), TargetController.YOU, false
        ), new ModeChoiceSourceCondition("Phyrexian"), ruleTrigger2);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private MirrodinBesieged(final MirrodinBesieged card) {
        super(card);
    }

    @Override
    public MirrodinBesieged copy() {
        return new MirrodinBesieged(this);
    }
}

class MirrodinBesiegedEffect extends OneShotEffect {

    MirrodinBesiegedEffect() {
        super(Outcome.Benefit);
    }

    private MirrodinBesiegedEffect(final MirrodinBesiegedEffect effect) {
        super(effect);
    }

    @Override
    public MirrodinBesiegedEffect copy() {
        return new MirrodinBesiegedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        new DrawDiscardControllerEffect(1, 1).apply(game, source);
        Player player = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(source.getFirstTarget());
        if (player == null || opponent == null) {
            return false;
        }
        if (player.getGraveyard().getCards(StaticFilters.FILTER_CARD_ARTIFACT, source.getControllerId(), source, game).size() >= 15) {
            opponent.lost(game);
        }
        return true;
    }
}