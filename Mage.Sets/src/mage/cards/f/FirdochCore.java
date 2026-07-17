package mage.cards.f;

import java.util.UUID;
import mage.constants.SubType;
import mage.game.permanent.token.custom.CreatureToken;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.ChangelingAbility;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class FirdochCore extends CardImpl {

    public FirdochCore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.KINDRED, CardType.ARTIFACT}, "{3}");

        this.subtype.add(SubType.SHAPESHIFTER);

        // Changeling
        this.addAbility(new ChangelingAbility());

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());

        // {4}: This artifact becomes a 4/4 artifact creature until end of turn.
        this.addAbility(new SimpleActivatedAbility(
            new BecomesCreatureSourceEffect(
                new CreatureToken(4, 4).withType(CardType.ARTIFACT).withSubType(SubType.SHAPESHIFTER),
                CardType.ARTIFACT,
                Duration.EndOfTurn
            ).setText("this artifact becomes a 4/4 artifact creature until end of turn"),
            new GenericManaCost(4)
        ));
    }

    private FirdochCore(final FirdochCore card) {
        super(card);
    }

    @Override
    public FirdochCore copy() {
        return new FirdochCore(this);
    }
}
