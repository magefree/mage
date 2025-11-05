package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.DrawNthCardTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.permanent.token.ClueArtifactToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KnowledgeSeeker extends CardImpl {

    public KnowledgeSeeker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.FOX);
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever you draw your second card each turn, put a +1/+1 counter on this creature.
        this.addAbility(new DrawNthCardTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance())));

        // When this creature dies, create a Clue token.
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new ClueArtifactToken())));
    }

    private KnowledgeSeeker(final KnowledgeSeeker card) {
        super(card);
    }

    @Override
    public KnowledgeSeeker copy() {
        return new KnowledgeSeeker(this);
    }
}
