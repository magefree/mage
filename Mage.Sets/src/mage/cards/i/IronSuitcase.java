package mage.cards.i;

import java.util.UUID;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.game.permanent.token.custom.CreatureToken;

/**
 *
 * @author muz
 */
public final class IronSuitcase extends CardImpl {

    public IronSuitcase(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // When this artifact enters, scry 2.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ScryEffect(2)));

        // {3}: This artifact becomes a 3/3 Construct artifact creature with flying until end of turn.
        this.addAbility(new SimpleActivatedAbility(
            new BecomesCreatureSourceEffect(
                new CreatureToken(3, 3)
                    .withType(CardType.ARTIFACT)
                    .withSubType(SubType.CONSTRUCT)
                    .withAbility(FlyingAbility.getInstance()),
                CardType.ARTIFACT,
                Duration.EndOfTurn
            ).setText("this artifact becomes a 3/3 Construct artifact creature with flying until end of turn"),
            new GenericManaCost(3)
        ));
    }

    private IronSuitcase(final IronSuitcase card) {
        super(card);
    }

    @Override
    public IronSuitcase copy() {
        return new IronSuitcase(this);
    }
}
