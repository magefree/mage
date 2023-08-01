package mage.cards.c;

import mage.MageInt;
import mage.abilities.keyword.DevourAbility;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Caprichrome extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledArtifactPermanent("artifact");

    public Caprichrome(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.GOAT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Devour artifact 1
        this.addAbility(new DevourAbility(1, filter));
    }

    private Caprichrome(final Caprichrome card) {
        super(card);
    }

    @Override
    public Caprichrome copy() {
        return new Caprichrome(this);
    }
}
