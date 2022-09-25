package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.RavenousAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.TyranidToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TermagantSwarm extends CardImpl {

    private static final DynamicValue xValue = new SourcePermanentPowerCount(false);

    public TermagantSwarm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{X}{G}");

        this.subtype.add(SubType.TYRANID);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Ravenous
        this.addAbility(new RavenousAbility());

        // Death Frenzy -- When Termagant Swarm dies, create a number of 1/1 green Tyranid creature tokens equal to Termagant Swarm's power.
        this.addAbility(new DiesSourceTriggeredAbility(
                new CreateTokenEffect(new TyranidToken(), xValue)
                        .setText("create a number of 1/1 green Tyranid creature tokens equal to {this}'s power")
        ).withFlavorWord("Death Frenzy"));
    }

    private TermagantSwarm(final TermagantSwarm card) {
        super(card);
    }

    @Override
    public TermagantSwarm copy() {
        return new TermagantSwarm(this);
    }
}
