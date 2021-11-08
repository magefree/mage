package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SigardasSummons extends CardImpl {

    public SigardasSummons(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{W}{W}");

        // Creatures you control with +1/+1 counters on them have base power and toughness 4/4, have flying, and are Angels in addition to their other types.
        this.addAbility(new SimpleStaticAbility(new SigardasSummonsEffect()));
    }

    private SigardasSummons(final SigardasSummons card) {
        super(card);
    }

    @Override
    public SigardasSummons copy() {
        return new SigardasSummons(this);
    }
}

class SigardasSummonsEffect extends ContinuousEffectImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(CounterType.P1P1.getPredicate());
    }

    SigardasSummonsEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "creatures you control with +1/+1 counters on them have base power and toughness 4/4, " +
                "have flying, and are Angels in addition to their other types";
    }

    private SigardasSummonsEffect(final SigardasSummonsEffect effect) {
        super(effect);
    }

    @Override
    public SigardasSummonsEffect copy() {
        return new SigardasSummonsEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                filter, source.getControllerId(), source.getSourceId(), game
        )) {
            switch (layer) {
                case TypeChangingEffects_4:
                    permanent.addSubType(game, SubType.ANGEL);
                    return true;
                case AbilityAddingRemovingEffects_6:
                    permanent.addAbility(FlyingAbility.getInstance(), source.getSourceId(), game);
                    return true;
                case PTChangingEffects_7:
                    if (sublayer == SubLayer.SetPT_7b) {
                        permanent.getPower().setValue(4);
                        permanent.getToughness().setValue(4);
                        return true;
                    }
            }
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        switch (layer) {
            case TypeChangingEffects_4:
            case AbilityAddingRemovingEffects_6:
            case PTChangingEffects_7:
                return true;
        }
        return false;
    }
}
