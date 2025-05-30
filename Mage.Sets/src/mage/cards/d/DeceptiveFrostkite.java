package mage.cards.d;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.EntersBattlefieldEffect;
import mage.abilities.effects.common.CopyPermanentEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.util.functions.CopyApplier;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DeceptiveFrostkite extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("a creature you control with power 4 or greater");

    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 3));
    }

    private static final CopyApplier applier = new CopyApplier() {
        @Override
        public boolean apply(Game game, MageObject blueprint, Ability source, UUID copyToObjectId) {
            blueprint.addSubType(SubType.DRAGON);
            blueprint.getAbilities().add(FlyingAbility.getInstance());
            return true;
        }
    };

    public DeceptiveFrostkite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{U}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // You may have this creature enter as a copy of a creature you control with power 4 or greater, except it's a Dragon in addition to its other types and it has flying.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL,
                new EntersBattlefieldEffect(
                        new CopyPermanentEffect(filter, applier), "you may have this creature enter " +
                        "as a copy of a creature you control with power 4 or greater, " +
                        "except it's a Dragon in addition to its other types and it has flying", true
                )
        ));
    }

    private DeceptiveFrostkite(final DeceptiveFrostkite card) {
        super(card);
    }

    @Override
    public DeceptiveFrostkite copy() {
        return new DeceptiveFrostkite(this);
    }
}
