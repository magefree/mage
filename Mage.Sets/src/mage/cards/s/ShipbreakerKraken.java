
package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesMonstrousSourceTriggeredAbility;
import mage.abilities.effects.common.DontUntapInControllersUntapStepTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.MonstrosityAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class ShipbreakerKraken extends CardImpl {

    public ShipbreakerKraken(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{U}");
        this.subtype.add(SubType.KRAKEN);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // {6}{U}{U}: Monstrosity 4.
        this.addAbility(new MonstrosityAbility("{6}{U}{U}", 4));

        // When Shipbreaker Kraken becomes monstrous, tap up to four target creatures. Those creatures don't untap during their controllers' untap steps for as long as you control Shipbreaker Kraken.
        Ability ability = new BecomesMonstrousSourceTriggeredAbility(new TapTargetEffect());
        ability.addTarget(new TargetCreaturePermanent(0, 4));
        ability.addEffect(new DontUntapInControllersUntapStepTargetEffect(Duration.WhileControlled)
                .setText("Those creatures don't untap during their controllers' untap steps for as long as you control {this}"));
        this.addAbility(ability);
    }

    private ShipbreakerKraken(final ShipbreakerKraken card) {
        super(card);
    }

    @Override
    public ShipbreakerKraken copy() {
        return new ShipbreakerKraken(this);
    }
}
