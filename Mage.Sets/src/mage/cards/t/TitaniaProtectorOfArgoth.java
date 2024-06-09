
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.PutIntoGraveFromBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.common.FilterLandCard;
import mage.filter.common.FilterLandPermanent;
import mage.game.permanent.token.TitaniaProtectorOfArgothElementalToken;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author LevelX2
 */
public final class TitaniaProtectorOfArgoth extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("a land you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public TitaniaProtectorOfArgoth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // When Titania, Protector of Argoth enters the battlefield, return target land card from your graveyard to the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnFromGraveyardToBattlefieldTargetEffect(), false);
        ability.addTarget(new TargetCardInYourGraveyard(new FilterLandCard("land card from your graveyard")));
        this.addAbility(ability);

        // Whenever a land you control is put into a graveyard from the battlefield, create a 5/3 green Elemental creature token.
        ability = new PutIntoGraveFromBattlefieldAllTriggeredAbility(new CreateTokenEffect(new TitaniaProtectorOfArgothElementalToken()), false, filter, false);
        this.addAbility(ability);

    }

    private TitaniaProtectorOfArgoth(final TitaniaProtectorOfArgoth card) {
        super(card);
    }

    @Override
    public TitaniaProtectorOfArgoth copy() {
        return new TitaniaProtectorOfArgoth(this);
    }
}
