package mage.cards.u;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldOrDiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.SaprolingToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UndercellarMyconid extends CardImpl {

    public UndercellarMyconid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.FUNGUS);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Whenever Undercellar Myconid enters the battlefield or dies, create a 1/1 green Saproling creature token.
        this.addAbility(new EntersBattlefieldOrDiesSourceTriggeredAbility(
                new CreateTokenEffect(new SaprolingToken()), false
        ));

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());
    }

    private UndercellarMyconid(final UndercellarMyconid card) {
        super(card);
    }

    @Override
    public UndercellarMyconid copy() {
        return new UndercellarMyconid(this);
    }
}
