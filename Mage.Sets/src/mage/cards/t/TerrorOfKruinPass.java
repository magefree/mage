package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.WerewolfBackTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;

import java.util.UUID;

/**
 * @author North
 */
public final class TerrorOfKruinPass extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.WEREWOLF, "Werewolves");

    public TerrorOfKruinPass(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");
        this.subtype.add(SubType.WEREWOLF);

        // this card is the second face of double-faced card
        this.nightCard = true;

        this.color.setRed(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.addAbility(DoubleStrikeAbility.getInstance());

        // Werewolves you control have menace. (They can't be blocked except by two or more creatures.)
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new MenaceAbility(false), Duration.WhileOnBattlefield, filter, false
        )));

        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Terror of Kruin Pass.
        this.addAbility(new WerewolfBackTriggeredAbility());
    }

    private TerrorOfKruinPass(final TerrorOfKruinPass card) {
        super(card);
    }

    @Override
    public TerrorOfKruinPass copy() {
        return new TerrorOfKruinPass(this);
    }
}
