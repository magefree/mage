
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class GrinningDemon extends CardImpl {

    public GrinningDemon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}{B}");
        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // At the beginning of your upkeep, you lose 2 life.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new LoseLifeSourceControllerEffect(2)));
        // Morph {2}{B}{B}
        this.addAbility(new MorphAbility(this, new ManaCostsImpl<>("{2}{B}{B}")));
    }

    private GrinningDemon(final GrinningDemon card) {
        super(card);
    }

    @Override
    public GrinningDemon copy() {
        return new GrinningDemon(this);
    }
}
