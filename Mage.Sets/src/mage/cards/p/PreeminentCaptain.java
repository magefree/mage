package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreatureCard;

import java.util.UUID;

/**
 * @author Rafbill
 */
public final class PreeminentCaptain extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("Soldier creature card");

    static {
        filter.add(SubType.SOLDIER.getPredicate());
    }

    public PreeminentCaptain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.subtype.add(SubType.KITHKIN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(FirstStrikeAbility.getInstance());

        // Whenever Preeminent Captain attacks, you may put a Soldier creature card from your hand onto the battlefield tapped and attacking.
        this.addAbility(new AttacksTriggeredAbility(new PutCardFromHandOntoBattlefieldEffect(
                filter, false, true, true
        ), true));
    }

    private PreeminentCaptain(final PreeminentCaptain card) {
        super(card);
    }

    @Override
    public PreeminentCaptain copy() {
        return new PreeminentCaptain(this);
    }
}
