package mage.cards.w;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.permanent.token.BirdToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WingmantleChaplain extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("creature with defender you control");
    private static final FilterPermanent filter2
            = new FilterCreaturePermanent("another creature with defender");
    private static final Predicate<MageObject> predicate
            = new AbilityPredicate(DefenderAbility.class);

    static {
        filter.add(predicate);
        filter2.add(predicate);
        filter2.add(AnotherPredicate.instance);
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);

    public WingmantleChaplain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // When Wingmantle Chaplain enters the battlefield, create a 1/1 white Bird creature token with flying for each creature with defender you control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new BirdToken(), xValue)));

        // Whenever another creature with defender enters the battlefield under your control, create a 1/1 white Bird creature token with flying.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(new CreateTokenEffect(new BirdToken()), filter2));
    }

    private WingmantleChaplain(final WingmantleChaplain card) {
        super(card);
    }

    @Override
    public WingmantleChaplain copy() {
        return new WingmantleChaplain(this);
    }
}
