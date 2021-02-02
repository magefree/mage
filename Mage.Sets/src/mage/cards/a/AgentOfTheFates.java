
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.effects.common.SacrificeOpponentsEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.HeroicAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

/**
 *
 * @author LevelX2
 */
public final class AgentOfTheFates extends CardImpl {

    public AgentOfTheFates(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ASSASSIN);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());
        // Heroic - Whenever you cast a spell that targets Agent of the Fates, each opponent sacrifices a creature.
        this.addAbility(new HeroicAbility(new SacrificeOpponentsEffect(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT)));
    }

    private AgentOfTheFates(final AgentOfTheFates card) {
        super(card);
    }

    @Override
    public AgentOfTheFates copy() {
        return new AgentOfTheFates(this);
    }
}
