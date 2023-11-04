package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class HoverstonePilgrim extends CardImpl {

    public HoverstonePilgrim(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}");

        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Ward {2}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{2}")));

        // {2}: Put target card from a graveyard on the bottom of its owner's library.
        Ability ability = new SimpleActivatedAbility(
                new PutOnLibraryTargetEffect(false),
                new GenericManaCost(2)
        );
        ability.addTarget(new TargetCardInGraveyard());
        this.addAbility(ability);
    }

    private HoverstonePilgrim(final HoverstonePilgrim card) {
        super(card);
    }

    @Override
    public HoverstonePilgrim copy() {
        return new HoverstonePilgrim(this);
    }
}
