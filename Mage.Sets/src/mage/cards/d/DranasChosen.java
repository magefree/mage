package mage.cards.d;

import mage.MageInt;
import mage.abilities.abilityword.CohortAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.ZombieToken;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public final class DranasChosen extends CardImpl {

    public DranasChosen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.SHAMAN);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // <i>Cohort</i> &mdash; {T}, Tap an untapped Ally you control: Create a tapped 2/2 black Zombie creature token.
        this.addAbility(new CohortAbility(new CreateTokenEffect(new ZombieToken(), 1, true, false)));
    }

    private DranasChosen(final DranasChosen card) {
        super(card);
    }

    @Override
    public DranasChosen copy() {
        return new DranasChosen(this);
    }
}
