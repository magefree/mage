package mage.cards.l;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.GainLifeTargetControllerEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LayDownArms extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent(
            "creature with mana value less than or equal to the number of Plains you control"
    );

    static {
        filter.add(LayDownArmsPredicate.instance);
    }

    public LayDownArms(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{W}");

        // Exile target creature with mana value less than or equal to the number of Plains you control. Its controller gains 3 life.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
        this.getSpellAbility().addEffect(new GainLifeTargetControllerEffect(3));
        this.getSpellAbility().addHint(LayDownArmsPredicate.getHint());
    }

    private LayDownArms(final LayDownArms card) {
        super(card);
    }

    @Override
    public LayDownArms copy() {
        return new LayDownArms(this);
    }
}

enum LayDownArmsPredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;
    private static final DynamicValue xValue
            = new PermanentsOnBattlefieldCount(new FilterControlledPermanent(SubType.PLAINS));
    private static final Hint hint = new ValueHint("Plains you control", xValue);

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        return input.getObject().getManaValue() <= xValue.calculate(game, input.getSource(), null);
    }

    public static Hint getHint() {
        return hint;
    }
}
