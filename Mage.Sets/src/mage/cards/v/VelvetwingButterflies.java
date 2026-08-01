package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class VelvetwingButterflies extends AdventureCard {

    public VelvetwingButterflies(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.INSTANT}, "{2}{W}", "Gaze in Wonder", "{1}{W}");

        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Gaze in Wonder
        // Tap one or two target creatures.
        this.getSpellCard().getSpellAbility().addEffect(new TapTargetEffect());
        this.getSpellCard().getSpellAbility().addTarget(new TargetPermanent(1, 2, StaticFilters.FILTER_PERMANENT_CREATURES));

        this.finalizeAdventure();
    }

    private VelvetwingButterflies(final VelvetwingButterflies card) {
        super(card);
    }

    @Override
    public VelvetwingButterflies copy() {
        return new VelvetwingButterflies(this);
    }
}
