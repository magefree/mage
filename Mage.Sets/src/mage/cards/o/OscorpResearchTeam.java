package mage.cards.o;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OscorpResearchTeam extends CardImpl {

    public OscorpResearchTeam(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCIENTIST);
        this.power = new MageInt(1);
        this.toughness = new MageInt(5);

        // {6}{U}: Draw two cards.
        this.addAbility(new SimpleActivatedAbility(new DrawCardSourceControllerEffect(2), new ManaCostsImpl<>("{6}{U}")));
    }

    private OscorpResearchTeam(final OscorpResearchTeam card) {
        super(card);
    }

    @Override
    public OscorpResearchTeam copy() {
        return new OscorpResearchTeam(this);
    }
}
