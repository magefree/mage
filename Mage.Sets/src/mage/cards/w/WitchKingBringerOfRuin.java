package mage.cards.w;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WitchKingBringerOfRuin extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("creature with the least power among creatures you control");

    static {
        filter.add(WitchKingBringerOfRuinPredicate.instance);
    }

    public WitchKingBringerOfRuin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.WRAITH);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Witch-king, Bringer of Ruin attacks, defending player sacrifices a creature with the least power among creatures they control.
        this.addAbility(new AttacksTriggeredAbility(
                new SacrificeEffect(filter, 1, "")
                        .setText("defending player sacrifices a creature " +
                                "with the least power among creatures they control"),
                false, null, SetTargetPointer.PLAYER
        ));
    }

    private WitchKingBringerOfRuin(final WitchKingBringerOfRuin card) {
        super(card);
    }

    @Override
    public WitchKingBringerOfRuin copy() {
        return new WitchKingBringerOfRuin(this);
    }
}

enum WitchKingBringerOfRuinPredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        return input
                .getObject()
                .getPower()
                .getValue()
                <= game
                .getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_CONTROLLED_CREATURE,
                        input.getPlayerId(), input.getSource(), game
                )
                .stream()
                .map(MageObject::getPower)
                .mapToInt(MageInt::getValue)
                .min()
                .orElse(0);
    }
}
