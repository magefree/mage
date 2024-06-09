package mage.cards.m;

import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.token.TreasureAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.game.permanent.token.custom.CreatureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Mimic extends CardImpl {

    public Mimic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.subtype.add(SubType.TREASURE);

        // {T}, Sacrifice Mimic: Add one mana of any color.
        this.addAbility(new TreasureAbility(true));

        // {2}: Mimic becomes a Shapeshifter artifact creature with base power and toughness 3/3 until end of turn.
        this.addAbility(new SimpleActivatedAbility(new BecomesCreatureSourceEffect(
                new CreatureToken(
                        3, 3, "Shapeshifter artifact creature with base power and toughness 3/3"
                ).withType(CardType.ARTIFACT).withSubType(SubType.SHAPESHIFTER), null, Duration.EndOfTurn
        ), new GenericManaCost(2)));
    }

    private Mimic(final Mimic card) {
        super(card);
    }

    @Override
    public Mimic copy() {
        return new Mimic(this);
    }
}
