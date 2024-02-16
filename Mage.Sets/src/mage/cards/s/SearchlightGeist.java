
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 * @author noxx
 */
public final class SearchlightGeist extends CardImpl {

    public SearchlightGeist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        this.addAbility(FlyingAbility.getInstance());

        // {3}{B}: Searchlight Geist gains deathtouch until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new GainAbilitySourceEffect(DeathtouchAbility.getInstance(), Duration.EndOfTurn),
                new ManaCostsImpl<>("{3}{B}")));
    }

    private SearchlightGeist(final SearchlightGeist card) {
        super(card);
    }

    @Override
    public SearchlightGeist copy() {
        return new SearchlightGeist(this);
    }
}
