package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CrossroadsCandleguide extends CardImpl {

    public CrossroadsCandleguide(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");

        this.subtype.add(SubType.SCARECROW);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // When Crossroads Candleguide enters the battlefield, exile up to one target card from a graveyard.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileTargetEffect());
        ability.addTarget(new TargetCardInGraveyard(0, 1));
        this.addAbility(ability);

        // {2}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility(new GenericManaCost(2)));
    }

    private CrossroadsCandleguide(final CrossroadsCandleguide card) {
        super(card);
    }

    @Override
    public CrossroadsCandleguide copy() {
        return new CrossroadsCandleguide(this);
    }
}
