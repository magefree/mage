package mage.cards.m;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.EntersBattlefieldEffect;
import mage.abilities.effects.common.CopyPermanentEffect;
import mage.abilities.effects.common.combat.GoadAllEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;
import mage.util.functions.CopyApplier;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MockingDoppelganger extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(MockingDoppelgangerPredicate.instance);
    }

    private static final CopyApplier applier = new CopyApplier() {
        @Override
        public boolean apply(Game game, MageObject blueprint, Ability source, UUID copyToObjectId) {
            blueprint.getAbilities().add(new SimpleStaticAbility(new GoadAllEffect(
                    Duration.WhileOnBattlefield, filter, false
            ).setText("other creatures with the same name as this creature are goaded")));
            return true;
        }
    };

    public MockingDoppelganger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // You may have Mocking Doppelganger enter the battlefield as a copy of a creature an opponent controls, except it has "Other creatures with the same name as this creature are goaded."
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL,
                new EntersBattlefieldEffect(new CopyPermanentEffect(
                        StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE, applier
                ).setText("You may have {this} enter the battlefield as a copy of a creature an opponent controls, " +
                        "except it has \"Other creatures with the same name as this creature are goaded.\""), "", true)
        ));
    }

    private MockingDoppelganger(final MockingDoppelganger card) {
        super(card);
    }

    @Override
    public MockingDoppelganger copy() {
        return new MockingDoppelganger(this);
    }
}

enum MockingDoppelgangerPredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        return Optional
                .ofNullable(input.getSource().getSourcePermanentIfItStillExists(game))
                .map(permanent -> CardUtil.haveSameNames(permanent, input.getObject()))
                .orElse(false);
    }
}
