package mage.cards.i;

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
public final class IllusionaryInformant extends CardImpl {

    public IllusionaryInformant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.ILLUSION);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // TODO: Draft specific abilities not implemented
        // Draft Illusionary Informant face up.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new InfoEffect("Draft Illusionary Informant face up - not implemented.")));

        // During the draft, you may turn Illusionary Informant face down. If you do, look at the next card drafted by a player of your choice.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new InfoEffect("During the draft, you may turn Illusionary Informant face down. If you do, "
                + "look at the next card drafted by a player of your choice - not implemented.")));

        // Flying
        this.addAbility(FlyingAbility.getInstance());
    }

    private IllusionaryInformant(final IllusionaryInformant card) {
        super(card);
    }

    @Override
    public IllusionaryInformant copy() {
        return new IllusionaryInformant(this);
    }
}
