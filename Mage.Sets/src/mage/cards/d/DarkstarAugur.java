package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.RevealPutInHandLoseLifeEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.OffspringAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DarkstarAugur extends CardImpl {

    public DarkstarAugur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.BAT);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Offspring {B}
        this.addAbility(new OffspringAbility("{B}"));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of your upkeep, reveal the top card of your library and put that card into your hand. You lose life equal to its mana value.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new RevealPutInHandLoseLifeEffect(), false));
    }

    private DarkstarAugur(final DarkstarAugur card) {
        super(card);
    }

    @Override
    public DarkstarAugur copy() {
        return new DarkstarAugur(this);
    }
}
