package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.AttacksPlayerWithCreaturesTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.FoodToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MeriadocBrandybuck extends CardImpl {
    FilterPermanent filter = new FilterControlledPermanent(SubType.HALFLING,"Halflings you control");

    public MeriadocBrandybuck(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HALFLING);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever one or more Halflings you control attack a player, create a Food token.
        this.addAbility(new AttacksPlayerWithCreaturesTriggeredAbility(new CreateTokenEffect(new FoodToken()), filter, SetTargetPointer.NONE));
    }

    private MeriadocBrandybuck(final MeriadocBrandybuck card) {
        super(card);
    }

    @Override
    public MeriadocBrandybuck copy() {
        return new MeriadocBrandybuck(this);
    }
}
