

package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.PlayAdditionalLandsControllerEffect;
import mage.abilities.effects.common.continuous.PlayTheTopCardEffect;
import mage.abilities.effects.common.continuous.PlayWithTheTopCardRevealedEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterLandCard;

/**
 *
 * @author nantuko, BetaSteward_at_googlemail.com
 */
public final class OracleOfMulDaya extends CardImpl {

    public OracleOfMulDaya(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SHAMAN);


        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // You may play an additional land on each of your turns.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PlayAdditionalLandsControllerEffect(1, Duration.WhileOnBattlefield)));
        // Play with the top card of your library revealed.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PlayWithTheTopCardRevealedEffect()));
        // You may play the top card of your library if it's a land card.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PlayTheTopCardEffect(new FilterLandCard())));
    }

    public OracleOfMulDaya(final OracleOfMulDaya card) {
        super(card);
    }

    @Override
    public OracleOfMulDaya copy() {
        return new OracleOfMulDaya(this);
    }

}
