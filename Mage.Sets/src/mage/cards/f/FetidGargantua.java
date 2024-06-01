package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.OneOrMoreCountersAddedTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.keyword.AdaptAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FetidGargantua extends CardImpl {

    public FetidGargantua(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // {2}{B}: Adapt 2.
        this.addAbility(new AdaptAbility(1, "{2}{B}"));

        // Whenever one or more +1/+1 counters are put on Fetid Gargantua, you may draw two cards. If you do, you lose 2 life.
        Ability ability = new OneOrMoreCountersAddedTriggeredAbility(new DrawCardSourceControllerEffect(2), true);
        ability.addEffect(new LoseLifeSourceControllerEffect(2).concatBy("If you do,"));
        this.addAbility(ability);
    }

    private FetidGargantua(final FetidGargantua card) {
        super(card);
    }

    @Override
    public FetidGargantua copy() {
        return new FetidGargantua(this);
    }
}
