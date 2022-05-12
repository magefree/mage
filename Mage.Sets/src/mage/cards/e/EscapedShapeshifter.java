package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EscapedShapeshifter extends CardImpl {

    public EscapedShapeshifter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");

        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // As long as an opponent controls a creature with flying not named Escaped Shapeshifter, Escaped Shapeshifter has flying. The same is true for first strike, trample, and protection from any color.
        this.addAbility(new SimpleStaticAbility(new EscapedShapeshifterEffect()));
    }

    private EscapedShapeshifter(final EscapedShapeshifter card) {
        super(card);
    }

    @Override
    public EscapedShapeshifter copy() {
        return new EscapedShapeshifter(this);
    }
}

class EscapedShapeshifterEffect extends ContinuousEffectImpl {

    EscapedShapeshifterEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.addDependedToType(DependencyType.AddingAbility);
        staticText = "As long as an opponent controls a creature with flying not named Escaped Shapeshifter, " +
                "{this} has flying. The same is true for first strike, trample, and protection from any color.";
    }

    private EscapedShapeshifterEffect(final EscapedShapeshifterEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (sourcePermanent == null) {
            return false;
        }

        game.getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE,
                        source.getControllerId(), source, game
                ).stream()
                .filter(Objects::nonNull)
                .filter(permanent -> !permanent.getName().equals("Escaped Shapeshifter"))
                .map(Permanent::getAbilities)
                .flatMap(Collection::stream).filter(EscapedShapeshifterEffect::checkAbility)
                .forEach(ability -> sourcePermanent.addAbility(ability, source.getSourceId(), game));
        return true;
    }

    private static boolean checkAbility(Ability ability) {
        if (ability instanceof FlyingAbility
                || ability instanceof FirstStrikeAbility
                || ability instanceof TrampleAbility) {
            return true;
        }
        return ability instanceof ProtectionAbility
                && ((ProtectionAbility) ability)
                .getFilter()
                .getPredicates()
                .stream()
                .anyMatch(ColorPredicate.class::isInstance);
    }

    @Override
    public EscapedShapeshifterEffect copy() {
        return new EscapedShapeshifterEffect(this);
    }
}