
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class CausticCrawler extends CardImpl {

    public CausticCrawler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{B}");
        this.subtype.add(SubType.INSECT);

        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        LandfallAbility ability = new LandfallAbility(new BoostTargetEffect(-1, -1, Duration.EndOfTurn), true);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private CausticCrawler(final CausticCrawler card) {
        super(card);
    }

    @Override
    public CausticCrawler copy() {
        return new CausticCrawler(this);
    }
}
