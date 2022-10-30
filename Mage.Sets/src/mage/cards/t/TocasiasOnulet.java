package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.UnearthAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TocasiasOnulet extends CardImpl {

    public TocasiasOnulet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Tocasia's Onulet leaves the battlefield, you gain 2 life.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new GainLifeEffect(2), false));

        // Unearth {3}{W}
        this.addAbility(new UnearthAbility(new ManaCostsImpl<>("{3}{W}")));
    }

    private TocasiasOnulet(final TocasiasOnulet card) {
        super(card);
    }

    @Override
    public TocasiasOnulet copy() {
        return new TocasiasOnulet(this);
    }
}
