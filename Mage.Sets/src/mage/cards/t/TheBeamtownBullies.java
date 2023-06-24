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
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.Filter;
import mage.filter.FilterCard;
import mage.filter.FilterOpponent;
import mage.filter.FilterPlayer;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.other.ActivePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author Arketec
 */
public final class TheBeamtownBullies extends CardImpl {

    private static final FilterCard filter = new FilterCard("nonlegendary creature card");
    private static final FilterPlayer playerFilter = new FilterOpponent();

    static {
        filter.add(Predicates.not(SuperType.LEGENDARY.getPredicate()));
        filter.add(CardType.CREATURE.getPredicate());
        playerFilter.add(ActivePlayerPredicate.instance);
    }
    public TheBeamtownBullies(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
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

        // Choose a non-legendary creature to put on the battlefield under their control

        ability.addTarget(new TargetPlayer(1, 1, false, playerFilter));
        ability.addTarget(new TargetCardInYourGraveyard(filter));

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
    public TheBeamtownBulliesEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Target opponent whose turn it is puts target nonlegendary creature card from your graveyard onto the battlefield under their control. It gains haste. Goad it. At the beginning of the next end step, exile it.";
    }
    private TheBeamtownBulliesEffect(final TheBeamtownBulliesEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(source.getFirstTarget());

        if (opponent != null)
        {
            // Put the chosen card onto the battlefield under opponents control
            Card card = game.getCard(source.getTargets().get(1).getFirstTarget());
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
