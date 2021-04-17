package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.WerewolfBackTriggeredAbility;
import mage.abilities.condition.common.TwoOrMoreSpellsWereCastLastTurnCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

/**
 *
 * @author fireshoes
 */
public final class BrandedHowler extends CardImpl {

    public BrandedHowler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"");
        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        this.color.setRed(true);

        // this card is the second face of double-faced card
        this.nightCard = true;
        this.transformable = true;

        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Branded Howler.
       this.addAbility(new WerewolfBackTriggeredAbility());
    }

    private BrandedHowler(final BrandedHowler card) {
        super(card);
    }

    @Override
    public BrandedHowler copy() {
        return new BrandedHowler(this);
    }
}
