package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author htrajan
 */
public final class SelflessGlyphweaver extends ModalDoubleFacedCard {

    public SelflessGlyphweaver(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.CLERIC}, "{2}{W}",
                "Deadly Vanity",
                new CardType[]{CardType.SORCERY}, new SubType[]{}, "{5}{B}{B}{B}"
        );

        // 1.
        // Selfless Glyphweaver
        // Creature - Human Cleric
        this.getLeftHalfCard().setPT(2, 3);

        // Exile Selfless Glyphweaver: Creatures you control gain indestructible until end of turn.
        this.getLeftHalfCard().addAbility(new SimpleActivatedAbility(new GainAbilityControlledEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURES), new ExileSourceCost()));

        // 2.
        // Deadly Vanity
        // Sorcery
        // Choose a creature or planeswalker, then destroy all other creatures and planeswalkers.
        this.getRightHalfCard().getSpellAbility().addEffect(new DeadlyVanityEffect());
    }

    private SelflessGlyphweaver(final SelflessGlyphweaver card) {
        super(card);
    }

    @Override
    public SelflessGlyphweaver copy() {
        return new SelflessGlyphweaver(this);
    }
}

class DeadlyVanityEffect extends OneShotEffect {

    DeadlyVanityEffect() {
        super(Outcome.Benefit);
        staticText = "choose a creature or planeswalker, then destroy all other creatures and planeswalkers";
    }

    DeadlyVanityEffect(DeadlyVanityEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        TargetPermanent target = new TargetCreatureOrPlaneswalker();
        target.setNotTarget(true);
        controller.choose(outcome, target, source, game);

        FilterPermanent filter = new FilterCreatureOrPlaneswalkerPermanent();
        UUID targetId = target.getFirstTarget();
        if (targetId != null) {
            filter.add(Predicates.not(new PermanentIdPredicate(targetId)));
        }
        return new DestroyAllEffect(filter).apply(game, source);
    }

    @Override
    public DeadlyVanityEffect copy() {
        return new DeadlyVanityEffect(this);
    }
}
