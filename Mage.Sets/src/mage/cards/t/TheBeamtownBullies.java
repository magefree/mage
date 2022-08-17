package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.combat.GoadTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author Arketec
 */
public final class TheBeamtownBullies extends CardImpl {

    public TheBeamtownBullies(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{R}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.DEVIL);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Vigilance, Haste
        this.addAbility(VigilanceAbility.getInstance());
        this.addAbility(HasteAbility.getInstance());

        // Target opponent whose turn it is puts target nonlegendary creature card from your graveyard onto the battlefield under their control. It gains haste. Goad it. At the beginning of the next end step, exile it.
        Ability ability = new SimpleActivatedAbility(new TheBeamtownBulliesEffect(), new TapSourceCost());
        this.addAbility(ability);
    }

    private TheBeamtownBullies(final TheBeamtownBullies card) {
        super(card);
    }

    @Override
    public TheBeamtownBullies copy() {
        return new TheBeamtownBullies(this);
    }
}

class TheBeamtownBulliesEffect extends OneShotEffect {
    private static final FilterCard filter = new FilterCard("nonlegendary creature card");

    static {
        filter.add(Predicates.not(SuperType.LEGENDARY.getPredicate()));
        filter.add(CardType.CREATURE.getPredicate());
    }
    public TheBeamtownBulliesEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Target opponent whose turn it is puts target nonlegendary creature card from your graveyard onto the battlefield under their control. It gains haste. Goad it. At the beginning of the next end step, exile it.";
    }
    private TheBeamtownBulliesEffect(final TheBeamtownBulliesEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(game.getActivePlayerId());
        // check to ensure it is an opponent's turn
        if (opponent != null && opponent.hasOpponent(source.getControllerId(), game))
        {
            // Get Cards from controller's graveyard
            Cards cards = new CardsImpl(game.getPlayer(source.getControllerId()).getGraveyard().getCards(game));

            // Choose a non-legendary creature to put on the battlefield under their control
            Player controller = game.getPlayer(source.getControllerId());
            TargetCard target = new TargetCard(1, Zone.GRAVEYARD, filter);
            controller.chooseTarget(outcome, cards, target, source, game);
            Card card = game.getCard(target.getFirstTarget());

            // Put the chosen card onto the battlefield under opponents control
            if (card == null || !opponent.moveCards(card, Zone.BATTLEFIELD, source, game)) {
                return false;
            }

            // Add continuous effects
            Permanent permanent = game.getPermanent(card.getId());
            if (permanent == null) {
                return false;
            }
            // Apply Haste
            ContinuousEffect hasteEffect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.Custom);
            hasteEffect.setTargetPointer(new FixedTarget(permanent, game));
            game.addEffect(hasteEffect, source);

            // Goad the creature
            GoadTargetEffect goadEffect = new GoadTargetEffect();
            goadEffect.setTargetPointer(new FixedTarget(permanent, game));
            game.addEffect(goadEffect, source);

            // Exile it at end of turn
            Effect exileEffect = new ExileTargetEffect("At the beginning of the next end step, exile it");
            exileEffect.setTargetPointer(new FixedTarget(permanent, game));
            DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(exileEffect);
            game.addDelayedTriggeredAbility(delayedAbility, source);
            return true;
        }
        return false;
    }

    @Override
    public Effect copy() {
        return new TheBeamtownBulliesEffect(this);
    }
}
