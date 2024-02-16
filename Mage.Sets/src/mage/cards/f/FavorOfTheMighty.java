
package mage.cards.f;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author emerald000
 */
public final class FavorOfTheMighty extends CardImpl {

    public FavorOfTheMighty(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.TRIBAL, CardType.ENCHANTMENT}, "{1}{W}");
        this.subtype.add(SubType.GIANT);

        // Each creature with the highest converted mana cost has protection from all colors.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new FavorOfTheMightyEffect()));
    }

    private FavorOfTheMighty(final FavorOfTheMighty card) {
        super(card);
    }

    @Override
    public FavorOfTheMighty copy() {
        return new FavorOfTheMighty(this);
    }
}

@SuppressWarnings("unchecked")
class FavorOfTheMightyEffect extends ContinuousEffectImpl {

    private static final FilterCard filter = new FilterCard("all colors");

    static {
        filter.add(Predicates.or(
                new ColorPredicate(ObjectColor.WHITE),
                new ColorPredicate(ObjectColor.BLUE),
                new ColorPredicate(ObjectColor.BLACK),
                new ColorPredicate(ObjectColor.RED),
                new ColorPredicate(ObjectColor.GREEN)));
    }

    FavorOfTheMightyEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.staticText = "Each creature with the highest mana value has protection from all colors.";
    }

    private FavorOfTheMightyEffect(final FavorOfTheMightyEffect effect) {
        super(effect);
    }

    @Override
    public FavorOfTheMightyEffect copy() {
        return new FavorOfTheMightyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int maxCMC = Integer.MIN_VALUE;
        for (Permanent permanent : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), game)) {
            if (permanent != null && permanent.getManaValue() > maxCMC) {
                maxCMC = permanent.getManaValue();
            }
        }
        FilterPermanent filterMaxCMC = new FilterCreaturePermanent();
        filterMaxCMC.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, maxCMC));
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filterMaxCMC, source.getControllerId(), game)) {
            if (permanent != null) {
                permanent.addAbility(new ProtectionAbility(filter), source.getSourceId(), game);
            }
        }
        return true;
    }
}
