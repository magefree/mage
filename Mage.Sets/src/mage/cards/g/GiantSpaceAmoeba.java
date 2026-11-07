package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.CreateTokenCopySourceEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.DefendingPlayerControlsSourceAttackingPredicate;
import mage.target.TargetPermanent;

/**
 * @author muz
 */
public final class GiantSpaceAmoeba extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature defending player controls");

    static {
        filter.add(DefendingPlayerControlsSourceAttackingPredicate.instance);
    }

    public GiantSpaceAmoeba(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");
        this.subtype.add(SubType.GERM);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever this creature attacks, target creature defending player controls gets -3/-0 until end of turn,
        // then create a token that's a copy of this creature.
        Ability ability = new AttacksTriggeredAbility(new BoostTargetEffect(-3, 0, Duration.EndOfTurn));
        ability.addTarget(new TargetPermanent(filter));
        ability.addEffect(new CreateTokenCopySourceEffect().concatBy(", then"));
        this.addAbility(ability);
    }

    private GiantSpaceAmoeba(final GiantSpaceAmoeba card) {
        super(card);
    }

    @Override
    public GiantSpaceAmoeba copy() {
        return new GiantSpaceAmoeba(this);
    }
}
