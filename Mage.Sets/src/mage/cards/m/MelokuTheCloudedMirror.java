package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ReturnToHandChosenControlledPermanentCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.MelokuTheCloudedMirrorToken;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author Loki
 */
public final class MelokuTheCloudedMirror extends CardImpl {

    public MelokuTheCloudedMirror(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MOONFOLK);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {1}, Return a land you control to its owner's hand: Create a 1/1 blue Illusion creature token with flying.
        Ability ability = new SimpleActivatedAbility(new CreateTokenEffect(
                new MelokuTheCloudedMirrorToken(), 1
        ), new GenericManaCost(1));
        ability.addCost(new ReturnToHandChosenControlledPermanentCost(
                new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_LAND_SHORT_TEXT)
        ));
        this.addAbility(ability);
    }

    private MelokuTheCloudedMirror(final MelokuTheCloudedMirror card) {
        super(card);
    }

    @Override
    public MelokuTheCloudedMirror copy() {
        return new MelokuTheCloudedMirror(this);
    }
}
