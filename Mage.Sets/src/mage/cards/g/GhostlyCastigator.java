package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.PutIntoGraveFromAnywhereSourceAbility;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.abilities.effects.common.ShuffleIntoLibraryTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GhostlyCastigator extends CardImpl {

    public GhostlyCastigator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);
        this.color.setBlue(true);
        this.nightCard = true;

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Ghostly Castigator enters the battlefield, you may shuffle up to three target cards from your graveyard into your library.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ShuffleIntoLibraryTargetEffect(), true);
        ability.addTarget(new TargetCardInYourGraveyard(0, 3));
        this.addAbility(ability);

        // If Ghostly Castigator would be put into a graveyard from anywhere, exile it instead.
        this.addAbility(new PutIntoGraveFromAnywhereSourceAbility(new ExileSourceEffect().setText("exile it instead")));
    }

    private GhostlyCastigator(final GhostlyCastigator card) {
        super(card);
    }

    @Override
    public GhostlyCastigator copy() {
        return new GhostlyCastigator(this);
    }
}
