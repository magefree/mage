
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.RevealLibraryPutIntoHandEffect;
import mage.abilities.keyword.AffinityForArtifactsAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterArtifactCard;

/**
 *
 * @author fireshoes
 */
public final class ChromescaleDrake extends CardImpl {

    public ChromescaleDrake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{U}{U}{U}");
        this.subtype.add(SubType.DRAKE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Affinity for artifacts
        this.addAbility(new AffinityForArtifactsAbility());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Chromescale Drake enters the battlefield, reveal the top three cards of your library. Put all artifact cards revealed this way into your hand and the rest into your graveyard.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new RevealLibraryPutIntoHandEffect(3, new FilterArtifactCard("artifact cards"), Zone.GRAVEYARD)));
    }

    private ChromescaleDrake(final ChromescaleDrake card) {
        super(card);
    }

    @Override
    public ChromescaleDrake copy() {
        return new ChromescaleDrake(this);
    }
}
