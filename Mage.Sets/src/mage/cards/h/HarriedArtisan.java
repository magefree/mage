package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HarriedArtisan extends CardImpl {

    public HarriedArtisan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);
        this.secondSideCardClazz = mage.cards.p.PhyrexianSkyflayer.class;

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // {3}{W/P}: Transform Harried Artisan. Activate only as a sorcery.
        this.addAbility(new TransformAbility());
        this.addAbility(new ActivateAsSorceryActivatedAbility(new TransformSourceEffect(), new ManaCostsImpl<>("{3}{W/P}")));
    }

    private HarriedArtisan(final HarriedArtisan card) {
        super(card);
    }

    @Override
    public HarriedArtisan copy() {
        return new HarriedArtisan(this);
    }
}
