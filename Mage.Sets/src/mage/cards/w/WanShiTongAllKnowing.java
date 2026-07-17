package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.PutIntoLibraryOneOrMoreTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.PutOnTopOrBottomLibraryTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.SpiritWorldToken;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WanShiTongAllKnowing extends CardImpl {

    public WanShiTongAllKnowing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Wan Shi Tong enters, target nonland permanent's owner puts it into their library second from the top or on the bottom.
        Ability ability = new EntersBattlefieldTriggeredAbility(new PutOnTopOrBottomLibraryTargetEffect(2, false));
        ability.addTarget(new TargetNonlandPermanent());
        this.addAbility(ability);

        // Whenever one or more cards are put into a library from anywhere, create two 1/1 colorless Spirit creature tokens with "This token can't block or be blocked by non-Spirit creatures."
        this.addAbility(new PutIntoLibraryOneOrMoreTriggeredAbility(new CreateTokenEffect(new SpiritWorldToken(), 2)));
    }

    private WanShiTongAllKnowing(final WanShiTongAllKnowing card) {
        super(card);
    }

    @Override
    public WanShiTongAllKnowing copy() {
        return new WanShiTongAllKnowing(this);
    }
}
