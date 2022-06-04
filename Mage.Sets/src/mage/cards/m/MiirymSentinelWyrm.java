package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MiirymSentinelWyrm extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.DRAGON);

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TokenPredicate.FALSE);
    }

    public MiirymSentinelWyrm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{U}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Ward {2}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{2}")));

        // Whenever another nontoken Dragon enters the battlefield under your control, create a token that's a copy of it, except the token isn't legendary if that Dragon is legendary.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                Zone.BATTLEFIELD, new CreateTokenCopyTargetEffect(true).setIsntLegendary(true),
                filter, false, SetTargetPointer.PERMANENT, "Whenever another nontoken Dragon " +
                "enters the battlefield under your control, create a token that's a copy of it, " +
                "except the token isn't legendary if that Dragon is legendary."
        ));
    }

    private MiirymSentinelWyrm(final MiirymSentinelWyrm card) {
        super(card);
    }

    @Override
    public MiirymSentinelWyrm copy() {
        return new MiirymSentinelWyrm(this);
    }
}
