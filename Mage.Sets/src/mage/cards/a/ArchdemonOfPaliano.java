package mage.cards.a;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.InfoEffect;

import java.util.UUID;

/**
 * @author tiera3 - based on ChardalynDragon and CanalDredger
 * note - draftmatters ability not implemented
 */
public final class ArchdemonOfPaliano extends CardImpl {

    public ArchdemonOfPaliano(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");

        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // TODO: Draft specific abilities not implemented
        // Draft Archdemon of Paliano face up.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new InfoEffect("Draft Archdemon of Paliano face up - not implemented.")));

        // As long as Archdemon of Paliano is face up during the draft, you can’t look at booster packs and must draft cards at random. After you draft three cards this way, turn Archdemon of Paliano face down.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new InfoEffect("As long as Archdemon of Paliano is face up during the draft, "
                + "you can't look at booster packs and must draft cards at random. "
                + "After you draft three cards this way, turn Archdemon of Paliano face down. - not implemented.")));

        // Flying
        this.addAbility(FlyingAbility.getInstance());
    }

    private ArchdemonOfPaliano(final ArchdemonOfPaliano card) {
        super(card);
    }

    @Override
    public ArchdemonOfPaliano copy() {
        return new ArchdemonOfPaliano(this);
    }
}
