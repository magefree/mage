package mage.cards.a;

import java.util.Optional;
import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.common.DealsDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TreasureToken;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class AntManElusiveAvenger extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures with greater power");

    static {
        filter.add(AntManElusiveAvengerPredicate.instance);
    }

    public AntManElusiveAvenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Ant-Man can't be blocked by creatures with greater power.
        this.addAbility(new SimpleStaticAbility(new CantBeBlockedByCreaturesSourceEffect(filter, Duration.WhileOnBattlefield)));

        // Whenever Ant-Man deals combat damage to a player, create that many Treasure tokens.
        this.addAbility(new DealsDamageToAPlayerTriggeredAbility(
            new CreateTokenEffect(new TreasureToken(), SavedDamageValue.MANY), false
        ));
    }

    private AntManElusiveAvenger(final AntManElusiveAvenger card) {
        super(card);
    }

    @Override
    public AntManElusiveAvenger copy() {
        return new AntManElusiveAvenger(this);
    }
}

enum AntManElusiveAvengerPredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        return Optional
            .ofNullable(input.getSource().getSourcePermanentIfItStillExists(game))
            .map(MageObject::getPower)
            .map(MageInt::getValue)
            .map(x -> x < input.getObject().getPower().getValue())
            .orElse(false);
    }
}
