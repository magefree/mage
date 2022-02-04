package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.BecomesTappedSourceTriggeredAbility;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MoonfolkPuzzlemaker extends CardImpl {

    public MoonfolkPuzzlemaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.MOONFOLK);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Moonfolk Puzzlemaker becomes tapped, scry 1.
        this.addAbility(new BecomesTappedSourceTriggeredAbility(new ScryEffect(1)));
    }

    private MoonfolkPuzzlemaker(final MoonfolkPuzzlemaker card) {
        super(card);
    }

    @Override
    public MoonfolkPuzzlemaker copy() {
        return new MoonfolkPuzzlemaker(this);
    }
}
