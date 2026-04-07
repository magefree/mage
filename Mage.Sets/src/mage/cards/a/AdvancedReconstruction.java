package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.CardsLeaveGraveyardTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.continuous.GainClassAbilitySourceEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.keyword.ClassLevelAbility;
import mage.abilities.keyword.ClassReminderAbility;
import mage.abilities.triggers.BeginningOfFirstMainTriggeredAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.card.CastFromZonePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AdvancedReconstruction extends CardImpl {

    private static final FilterCard filter = new FilterCard("spells you cast from anywhere other than your hand");

    static {
        filter.add(Predicates.not(new CastFromZonePredicate(Zone.HAND)));
    }

    public AdvancedReconstruction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}");

        this.subtype.add(SubType.CLASS);

        // (Gain the next level as a sorcery to add its ability.)
        this.addAbility(new ClassReminderAbility());

        // At the beginning of your first main phase, mill a card, then exile a card from your graveyard at random. You may play the exiled card this turn.
        Ability ability = new BeginningOfFirstMainTriggeredAbility(new MillCardsControllerEffect(1));
        ability.addEffect(new AdvancedReconstructionEffect());
        this.addAbility(ability);

        // {1}{R}: Level 2
        this.addAbility(new ClassLevelAbility(2, "{1}{R}"));

        // Whenever one or more cards leave your graveyard, this Class deals 2 damage to each opponent.
        this.addAbility(new SimpleStaticAbility(new GainClassAbilitySourceEffect(
                new CardsLeaveGraveyardTriggeredAbility(new DamagePlayersEffect(2, TargetController.OPPONENT)), 2
        )));

        // {1}{R}: Level 3
        this.addAbility(new ClassLevelAbility(3, "{1}{R}"));

        // Spells you cast from anywhere other than your hand cost {2} less to cast.
        this.addAbility(new SimpleStaticAbility(new GainClassAbilitySourceEffect(
                new SpellsCostReductionControllerEffect(filter, 2), 3
        )));
    }

    private AdvancedReconstruction(final AdvancedReconstruction card) {
        super(card);
    }

    @Override
    public AdvancedReconstruction copy() {
        return new AdvancedReconstruction(this);
    }
}

class AdvancedReconstructionEffect extends OneShotEffect {

    AdvancedReconstructionEffect() {
        super(Outcome.Benefit);
        staticText = ", then exile a card from your graveyard at random. You may play the exiled card this turn";
    }

    private AdvancedReconstructionEffect(final AdvancedReconstructionEffect effect) {
        super(effect);
    }

    @Override
    public AdvancedReconstructionEffect copy() {
        return new AdvancedReconstructionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Card card = player.getGraveyard().getRandom(game);
        if (card == null) {
            return false;
        }
        player.moveCards(card, Zone.EXILED, source, game);
        CardUtil.makeCardPlayable(game, source, card, false, Duration.EndOfTurn, false);
        return true;
    }
}
