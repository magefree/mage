package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.RequirementEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ColorlessPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class GrizzledAngler extends TransformingDoubleFacedCard {

    private static final FilterCard filter = new FilterCreatureCard();

    static {
        filter.add(ColorlessPredicate.instance);
    }

    private static final Condition condition = new CardsInControllerGraveyardCondition(1, filter);

    public GrizzledAngler(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN}, "{2}{U}",
                "Grisly Anglerfish",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.ELDRAZI, SubType.FISH}, ""
        );
        this.getLeftHalfCard().setPT(2, 3);
        this.getRightHalfCard().setPT(4, 5);

        // {T}: Put the top two cards of your library into your graveyard. Then if there is a colorless creature card in your graveyard, transform Grizzled Angler.
        Ability ability = new SimpleActivatedAbility(new MillCardsControllerEffect(2), new TapSourceCost());
        ability.addEffect(new ConditionalOneShotEffect(
                new TransformSourceEffect(), condition, "Then if there is " +
                "a colorless creature card in your graveyard, transform {this}"
        ));
        this.getLeftHalfCard().addAbility(ability);

        // Grisly Anglerfish
        // {6}: Creatures your opponents control attack this turn if able.
        this.getRightHalfCard().addAbility(new SimpleActivatedAbility(
                new GrislyAnglerfishEffect(), new GenericManaCost(6)
        ));
    }

    private GrizzledAngler(final GrizzledAngler card) {
        super(card);
    }

    @Override
    public GrizzledAngler copy() {
        return new GrizzledAngler(this);
    }
}

class GrislyAnglerfishEffect extends RequirementEffect {

    GrislyAnglerfishEffect() {
        super(Duration.EndOfTurn);
        staticText = "creatures your opponents control attack this turn if able";
    }

    private GrislyAnglerfishEffect(final GrislyAnglerfishEffect effect) {
        super(effect);
    }

    @Override
    public GrislyAnglerfishEffect copy() {
        return new GrislyAnglerfishEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return game.getOpponents(source.getControllerId()).contains(permanent.getControllerId());
    }

    @Override
    public boolean mustAttack(Game game) {
        return true;
    }

    @Override
    public boolean mustBlock(Game game) {
        return false;
    }
}
