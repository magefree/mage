
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class ReaperOfTheWilds extends CardImpl {

    public ReaperOfTheWilds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}{G}");
        this.subtype.add(SubType.GORGON);

        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Whenever another creature dies, scry 1.</i>
        this.addAbility(new DiesCreatureTriggeredAbility(new ScryEffect(1), false, true));
        // {B}: Reaper of the Wilds gains deathtouch until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilitySourceEffect(DeathtouchAbility.getInstance(), Duration.EndOfTurn), new ManaCostsImpl<>("{B}")));
        // {1}{G}: Reaper of the Wilds gains hexproof until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilitySourceEffect(HexproofAbility.getInstance(), Duration.EndOfTurn), new ManaCostsImpl<>("{1}{G}")));
    }

    private ReaperOfTheWilds(final ReaperOfTheWilds card) {
        super(card);
    }

    @Override
    public ReaperOfTheWilds copy() {
        return new ReaperOfTheWilds(this);
    }
}
