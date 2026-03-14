package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.DrawNthCardTriggeredAbility;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TigerSeal extends CardImpl {

    public TigerSeal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}");

        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.SEAL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // At the beginning of your upkeep, tap this creature.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new TapSourceEffect()));

        // Whenever you draw your second card each turn, untap this creature.
        this.addAbility(new DrawNthCardTriggeredAbility(new UntapSourceEffect()));
    }

    private TigerSeal(final TigerSeal card) {
        super(card);
    }

    @Override
    public TigerSeal copy() {
        return new TigerSeal(this);
    }
}
