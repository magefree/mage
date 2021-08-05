package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.GolemFlyingToken;
import mage.game.permanent.token.GolemTrampleToken;
import mage.game.permanent.token.GolemVigilanceToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TriplicateTitan extends CardImpl {

    public TriplicateTitan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{9}");

        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(9);
        this.toughness = new MageInt(9);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Triplicate Titan dies, create a 3/3 colorless Golem artifact creature token with flying, a 3/3 colorless Golem artifact creature token with vigilance, and a 3/3 colorless Golem artifact creature token with trample.
        Ability ability = new DiesSourceTriggeredAbility(new CreateTokenEffect(new GolemFlyingToken()));
        ability.addEffect(new CreateTokenEffect(new GolemVigilanceToken())
                .setText(", a 3/3 colorless Golem artifact creature token with vigilance"));
        ability.addEffect(new CreateTokenEffect(new GolemTrampleToken())
                .setText(", and a 3/3 colorless Golem artifact creature token with trample"));
        this.addAbility(ability);
    }

    private TriplicateTitan(final TriplicateTitan card) {
        super(card);
    }

    @Override
    public TriplicateTitan copy() {
        return new TriplicateTitan(this);
    }
}
