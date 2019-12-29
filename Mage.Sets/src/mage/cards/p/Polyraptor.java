package mage.cards.p;



import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.effects.CreateTokenCopySourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author JayDi85
 */
public final class Polyraptor extends CardImpl {

    public Polyraptor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{G}{G}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Enrage - Whenever Polyraptor is dealt damage, create a token that's a copy of Polyraptor.
        Ability ability = new DealtDamageToSourceTriggeredAbility(
                new CreateTokenCopySourceEffect(),
                false,
                true);
        this.addAbility(ability);
    }

    public Polyraptor(final Polyraptor card) {
        super(card);
    }

    @Override
    public Polyraptor copy() {
        return new Polyraptor(this);
    }
}