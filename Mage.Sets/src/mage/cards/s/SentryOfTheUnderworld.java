
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class SentryOfTheUnderworld extends CardImpl {

    public SentryOfTheUnderworld(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}{B}");
        this.subtype.add(SubType.GRIFFIN);
        this.subtype.add(SubType.SKELETON);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
        // {W}{B}, Pay 3 life: Regenerate Sentry of the Underworld.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new ManaCostsImpl<>("{W}{B}"));
        ability.addCost(new PayLifeCost(3));
        this.addAbility(ability);
    }

    private SentryOfTheUnderworld(final SentryOfTheUnderworld card) {
        super(card);
    }

    @Override
    public SentryOfTheUnderworld copy() {
        return new SentryOfTheUnderworld(this);
    }
}
