
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.DealsDamageToOpponentTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.abilities.effects.common.discard.DiscardHandTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;

/**
 *
 * @author LevelX2
 */
public final class NicolBolas extends CardImpl {

    public NicolBolas(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}{U}{B}{B}{R}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELDER);
        this.subtype.add(SubType.DRAGON);

        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // At the beginning of your upkeep, sacrifice Nicol Bolas unless you pay {U}{B}{R}.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new SacrificeSourceUnlessPaysEffect(new ManaCostsImpl<>("{U}{B}{R}")), TargetController.YOU, false));

        // Whenever Nicol Bolas deals damage to an opponent, that player discards their hand.
        this.addAbility(new DealsDamageToOpponentTriggeredAbility(new DiscardHandTargetEffect("that player"), false, false, true));
    }

    private NicolBolas(final NicolBolas card) {
        super(card);
    }

    @Override
    public NicolBolas copy() {
        return new NicolBolas(this);
    }
}
