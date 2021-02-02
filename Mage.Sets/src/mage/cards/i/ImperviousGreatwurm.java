package mage.cards.i;

import mage.abilities.keyword.ConvokeAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.MageInt;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 *
 * @author Rystan
 */

public final class ImperviousGreatwurm extends CardImpl {

    public ImperviousGreatwurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{7}{G}{G}{G}");
        this.subtype.add(SubType.WURM);

        this.power = new MageInt(16);
        this.toughness = new MageInt(16);

        // Convoke
        this.addAbility(new ConvokeAbility());
        
        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());
    }

    private ImperviousGreatwurm(final ImperviousGreatwurm card) {
        super(card);
    }

    @Override
    public ImperviousGreatwurm copy() {
        return new ImperviousGreatwurm(this);
    }
}
