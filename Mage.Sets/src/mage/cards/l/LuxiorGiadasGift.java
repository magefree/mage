package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.Counter;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPlaneswalkerPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.*;
import java.util.stream.IntStream;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 * @author TheElk801
 */
public final class LuxiorGiadasGift extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPlaneswalkerPermanent("planeswalker");

    public LuxiorGiadasGift(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+1 for each counter on it.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(
                LuxiorGiadasGiftValue.instance, LuxiorGiadasGiftValue.instance
        )));

        // Equipped permanent isn't a planeswalker and is a creature in addition to its other types.
        this.addAbility(new SimpleStaticAbility(new LuxiorGiadasGiftEffect()));

        // Equip planeswalker {1}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(1), new TargetPermanent(filter), false));

        // Equip {3}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(3), new TargetControlledCreaturePermanent(), false));
    }

    private LuxiorGiadasGift(final LuxiorGiadasGift card) {
        super(card);
    }

    @Override
    public LuxiorGiadasGift copy() {
        return new LuxiorGiadasGift(this);
    }
}

enum LuxiorGiadasGiftValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return Optional.of(sourceAbility.getSourcePermanentIfItStillExists(game))
                .filter(Objects::nonNull)
                .map(Permanent::getAttachedTo)
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .map(permanent -> permanent.getCounters(game))
                .map(HashMap::values)
                .map(Collection::stream)
                .map(x -> x.mapToInt(Counter::getCount))
                .map(IntStream::sum)
                .orElse(0);
    }

    @Override
    public LuxiorGiadasGiftValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "counter on it";
    }

    @Override
    public String toString() {
        return "1";
    }
}

class LuxiorGiadasGiftEffect extends ContinuousEffectImpl {

    LuxiorGiadasGiftEffect() {
        super(Duration.WhileOnBattlefield, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Benefit);
        staticText = "equipped permanent isn't a planeswalker and is a creature in addition to its other types";
    }

    private LuxiorGiadasGiftEffect(final LuxiorGiadasGiftEffect effect) {
        super(effect);
    }

    @Override
    public LuxiorGiadasGiftEffect copy() {
        return new LuxiorGiadasGiftEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Optional.of(source.getSourcePermanentIfItStillExists(game))
                .filter(Objects::nonNull)
                .map(Permanent::getAttachedTo)
                .map(game::getPermanent)
                .ifPresent(permanent -> {
                    permanent.removeCardType(game, CardType.PLANESWALKER);
                    permanent.removeAllSubTypes(game, SubTypeSet.PlaneswalkerType);
                    permanent.addCardType(game, CardType.CREATURE);
                });
        return true;
    }
}
