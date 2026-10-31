package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.CardsLeaveGraveyardTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.RomulanToken;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author muz
 */
public final class SelaTalShiarParadox extends CardImpl {

    public SelaTalShiarParadox(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ROMULAN);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Sela attacks, exile up to one target card from a graveyard.
        Ability ability = new AttacksTriggeredAbility(new ExileTargetEffect());
        ability.addTarget(new TargetCardInGraveyard(0, 1));
        this.addAbility(ability);

        // Whenever one or more cards leave your graveyard, create a tapped 2/2 black Romulan creature token.
        this.addAbility(new CardsLeaveGraveyardTriggeredAbility(new CreateTokenEffect(new RomulanToken(), 1, true)));
    }

    private SelaTalShiarParadox(final SelaTalShiarParadox card) {
        super(card);
    }

    @Override
    public SelaTalShiarParadox copy() {
        return new SelaTalShiarParadox(this);
    }
}
