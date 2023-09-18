package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.WerewolfBackTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author nantuko
 */
public final class WildbloodPack extends CardImpl {

    public WildbloodPack(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");
        this.subtype.add(SubType.WEREWOLF);
        this.color.setRed(true);

        // this card is the second face of double-faced card
        this.nightCard = true;

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        this.addAbility(TrampleAbility.getInstance());

        // Attacking creatures you control get +3/+0.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                3, 0, Duration.WhileOnBattlefield,
                StaticFilters.FILTER_ATTACKING_CREATURES, false
        )));

        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Wildblood Pack.
        this.addAbility(new WerewolfBackTriggeredAbility());

    }

    private WildbloodPack(final WildbloodPack card) {
        super(card);
    }

    @Override
    public WildbloodPack copy() {
        return new WildbloodPack(this);
    }
}
