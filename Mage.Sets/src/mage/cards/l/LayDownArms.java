package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.Optional;
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
        this.getSpellAbility().addEffect(new LayDownArmsEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
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

class LayDownArmsEffect extends OneShotEffect {

    LayDownArmsEffect() {
        super(Outcome.Benefit);
        staticText = "exile target creature with mana value less than or equal " +
                "to the number of Plains you control. Its controller gains 3 life";
    }

    private LayDownArmsEffect(final LayDownArmsEffect effect) {
        super(effect);
    }

    @Override
    public LayDownArmsEffect copy() {
        return new LayDownArmsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (controller == null || permanent == null) {
            return false;
        }
        controller.moveCards(permanent, Zone.EXILED, source, game);
        Optional.ofNullable(game.getPlayer(permanent.getControllerId()))
                .ifPresent(player -> player.gainLife(3, game, source));
        return true;
    }
}
