package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.IntimidateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author BetaSteward
 */
public final class Immerwolf extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Wolf and Werewolf creatures");

    static {
        filter.add(Predicates.or(SubType.WOLF.getPredicate(), SubType.WEREWOLF.getPredicate()));
    }

    public Immerwolf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}{G}");
        this.subtype.add(SubType.WOLF);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(IntimidateAbility.getInstance());

        // Other Wolf and Werewolf creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filter, true)));

        // Non-Human Werewolves you control can't transform.
        this.addAbility(new SimpleStaticAbility(new ImmerwolfEffect()));

    }

    private Immerwolf(final Immerwolf card) {
        super(card);
    }

    @Override
    public Immerwolf copy() {
        return new Immerwolf(this);
    }
}

class ImmerwolfEffect extends RestrictionEffect {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent(SubType.WEREWOLF);

    static {
        filter.add(Predicates.not(SubType.HUMAN.getPredicate()));
    }

    public ImmerwolfEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "Non-Human Werewolves you control can't transform";
    }

    private ImmerwolfEffect(final ImmerwolfEffect effect) {
        super(effect);
    }

    @Override
    public ImmerwolfEffect copy() {
        return new ImmerwolfEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return filter.match(permanent, source.getControllerId(), source, game);
    }

    @Override
    public boolean canTransform(Game game, boolean canUseChooseDialogs) {
        return false;
    }
}
