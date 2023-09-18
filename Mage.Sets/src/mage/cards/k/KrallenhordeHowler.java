package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.WerewolfBackTriggeredAbility;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class KrallenhordeHowler extends CardImpl {

    private static final FilterCard FILTER = new FilterCreatureCard("creature spells");

    public KrallenhordeHowler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");
        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.color.setGreen(true);

        // this card is the second face of double-faced card
        this.nightCard = true;

        // Creature spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(new SpellsCostReductionControllerEffect(FILTER, 1)));

        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Krallenhorde Howler.
        this.addAbility(new WerewolfBackTriggeredAbility());
    }

    private KrallenhordeHowler(final KrallenhordeHowler card) {
        super(card);
    }

    @Override
    public KrallenhordeHowler copy() {
        return new KrallenhordeHowler(this);
    }
}
