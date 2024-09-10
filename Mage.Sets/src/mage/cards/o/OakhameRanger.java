package mage.cards.o;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.game.permanent.token.HumanToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OakhameRanger extends AdventureCard {

    public OakhameRanger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.SORCERY}, "{G/W}{G/W}{G/W}{G/W}", "Bring Back", "{G/W}{G/W}{G/W}{G/W}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.KNIGHT);
        this.subtype.add(SubType.RANGER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {T}: Creatures you control get +1/+1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                new BoostControlledEffect(1, 1, Duration.EndOfTurn), new TapSourceCost()
        ));

        // Bring Back
        // Create two 1/1 white Human creature tokens.
        this.getSpellCard().getSpellAbility().addEffect(new CreateTokenEffect(new HumanToken(), 2));

        this.finalizeAdventure();
    }

    private OakhameRanger(final OakhameRanger card) {
        super(card);
    }

    @Override
    public OakhameRanger copy() {
        return new OakhameRanger(this);
    }
}
