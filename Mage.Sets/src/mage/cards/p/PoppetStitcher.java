package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.ZombieDecayedToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PoppetStitcher extends TransformingDoubleFacedCard {

    private static final FilterPermanent filter = new FilterControlledPermanent("you control three or more creature tokens");

    static {
        filter.add(TokenPredicate.TRUE);
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.MORE_THAN, 2);
    private static final Hint hint = new ValueHint(
            "Creature tokens you control", new PermanentsOnBattlefieldCount(filter)
    );

    public PoppetStitcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.WIZARD}, "{2}{U}",
                "Poppet Factory",
                new CardType[]{CardType.ARTIFACT}, new SubType[]{}, "U");

        // Poppet Stitcher
        this.getLeftHalfCard().setPT(2, 3);

        // Whenever you cast an instant or sorcery spell, create a 2/2 black Zombie creature token with decayed.
        this.getLeftHalfCard().addAbility(new SpellCastControllerTriggeredAbility(
                new CreateTokenEffect(new ZombieDecayedToken()),
                StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY, false
        ));

        // At the beginning of your upkeep, if you control three or more creature tokens, you may transform Poppet Sticher.
        this.getLeftHalfCard().addAbility(new BeginningOfUpkeepTriggeredAbility(new TransformSourceEffect(), true)
                .withInterveningIf(condition).addHint(hint));

        // Poppet Factory
        // Creature tokens you control lose all abilities and have base power and toughness 3/3.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new PoppetFactoryEffect()));

        // At the beginning of your upkeep, you may transform Poppet Factory.
        this.getRightHalfCard().addAbility(new BeginningOfUpkeepTriggeredAbility(
                new TransformSourceEffect(), true
        ));
    }

    private PoppetStitcher(final PoppetStitcher card) {
        super(card);
    }

    @Override
    public PoppetStitcher copy() {
        return new PoppetStitcher(this);
    }
}

class PoppetFactoryEffect extends ContinuousEffectImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(TokenPredicate.TRUE);
    }

    PoppetFactoryEffect() {
        super(Duration.WhileOnBattlefield, Outcome.LoseAbility);
        staticText = "creature tokens you control lose all abilities and have base power and toughness 3/3";
    }

    private PoppetFactoryEffect(final PoppetFactoryEffect effect) {
        super(effect);
    }

    @Override
    public PoppetFactoryEffect copy() {
        return new PoppetFactoryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                filter, source.getControllerId(), source, game
        )) {
            switch (layer) {
                case AbilityAddingRemovingEffects_6:
                    permanent.removeAllAbilities(source.getSourceId(), game);
                    break;
                case PTChangingEffects_7:
                    if (sublayer == SubLayer.SetPT_7b) {
                        permanent.getPower().setModifiedBaseValue(3);
                        permanent.getToughness().setModifiedBaseValue(3);
                    }
                    break;
            }
        }
        return true;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.PTChangingEffects_7
                || layer == Layer.AbilityAddingRemovingEffects_6;
    }
}
