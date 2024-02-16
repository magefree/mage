package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author TheElk801
 */
public final class MysticArchaeologist extends CardImpl {

    public MysticArchaeologist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {3}{U}{U}: Draw two cards.
        this.addAbility(new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new DrawCardSourceControllerEffect(2),
                new ManaCostsImpl<>("{3}{U}{U}")
        ));
    }

    private MysticArchaeologist(final MysticArchaeologist card) {
        super(card);
    }

    @Override
    public MysticArchaeologist copy() {
        return new MysticArchaeologist(this);
    }
}
