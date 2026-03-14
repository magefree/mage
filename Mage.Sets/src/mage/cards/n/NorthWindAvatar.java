package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.CastFromEverywhereSourceCondition;
import mage.abilities.effects.common.WishEffect;
import mage.abilities.hint.common.OpenSideboardHint;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class NorthWindAvatar extends CardImpl {

    public NorthWindAvatar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}{R}");

        this.subtype.add(SubType.DRAGON);
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When this creature enters, if you cast it, you may put a card you own from outside the game into your hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new WishEffect())
            .withInterveningIf(CastFromEverywhereSourceCondition.instance);
        ability.addHint(OpenSideboardHint.instance);
        this.addAbility(ability);
    }

    private NorthWindAvatar(final NorthWindAvatar card) {
        super(card);
    }

    @Override
    public NorthWindAvatar copy() {
        return new NorthWindAvatar(this);
    }
}
