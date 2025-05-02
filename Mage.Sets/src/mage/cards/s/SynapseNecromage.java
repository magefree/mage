package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.FungusCantBlockToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SynapseNecromage extends CardImpl {

    public SynapseNecromage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.FUNGUS);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // When Synapse Necromage dies, create two 1/1 black Fungus creature tokens with "This creature can't block."
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new FungusCantBlockToken(), 2)));
    }

    private SynapseNecromage(final SynapseNecromage card) {
        super(card);
    }

    @Override
    public SynapseNecromage copy() {
        return new SynapseNecromage(this);
    }
}
