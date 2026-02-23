package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.OrCost;
import mage.abilities.costs.common.BeholdCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.MerfolkWhiteBlueToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SilvergillMentor extends CardImpl {

    public SilvergillMentor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // As an additional cost to cast this spell, behold a Merfolk or pay {2}.
        this.getSpellAbility().addCost(new OrCost(
                "behold a Merfolk or pay {2}",
                new BeholdCost(SubType.MERFOLK), new GenericManaCost(2)
        ));

        // When this creature enters, create a 1/1 white and blue Merfolk creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new MerfolkWhiteBlueToken())));
    }

    private SilvergillMentor(final SilvergillMentor card) {
        super(card);
    }

    @Override
    public SilvergillMentor copy() {
        return new SilvergillMentor(this);
    }
}
