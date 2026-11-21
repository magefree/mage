package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.BorgToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author DarioBarrabinoDev
 */
public final class DeliverFromChaos extends CardImpl {

    public DeliverFromChaos(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{W}{B}");

        // Exile target artifact or creature. Create X 1/1 colorless Borg artifact creature tokens, where X is that permanent's mana value.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addEffect(new CreateTokenEffect(new BorgToken(), DeliverFromChaosValue.instance));
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_CREATURE));
    }

    private DeliverFromChaos(final DeliverFromChaos card) {
        super(card);
    }

    @Override
    public DeliverFromChaos copy() {
        return new DeliverFromChaos(this);
    }

    private enum DeliverFromChaosValue implements DynamicValue {
        instance;

        @Override
        public int calculate(Game game, Ability sourceAbility, Effect effect) {
            // Use the target as it last existed on the battlefield if it has already left.
            Permanent permanent = effect.getTargetPointer().getFirstTargetPermanentOrLKI(game, sourceAbility);
            return permanent == null ? 0 : permanent.getManaValue();
        }

        @Override
        public DeliverFromChaosValue copy() {
            return this;
        }

        @Override
        public String toString() {
            return "X";
        }

        @Override
        public String getMessage() {
            return "that permanent's mana value";
        }
    }
}
