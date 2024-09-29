package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateOncePerGameActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PossessedGoat extends CardImpl {

    public PossessedGoat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.GOAT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {3}, Discard a card: Put three +1/+1 counters on Possessed Goat and it becomes a black Demon in addition to its other colors and types. Activate only once.
        Ability ability = new ActivateOncePerGameActivatedAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(3)), new GenericManaCost(3)
        );
        ability.addCost(new DiscardCardCost());
        ability.addEffect(new PossessedGoatEffect());
        this.addAbility(ability);
    }

    private PossessedGoat(final PossessedGoat card) {
        super(card);
    }

    @Override
    public PossessedGoat copy() {
        return new PossessedGoat(this);
    }
}

class PossessedGoatEffect extends ContinuousEffectImpl {

    PossessedGoatEffect() {
        super(Duration.Custom, Outcome.Benefit);
        staticText = "and it becomes a black Demon in addition to its other colors and types";
    }

    private PossessedGoatEffect(final PossessedGoatEffect effect) {
        super(effect);
    }

    @Override
    public PossessedGoatEffect copy() {
        return new PossessedGoatEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null) {
            discard();
            return false;
        }
        switch (layer) {
            case TypeChangingEffects_4:
                permanent.addSubType(game, SubType.DEMON);
                return true;
            case ColorChangingEffects_5:
                permanent.getColor(game).setBlack(true);
                return true;
        }
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        switch (layer) {
            case TypeChangingEffects_4:
            case ColorChangingEffects_5:
                return true;
        }
        return false;
    }
}
