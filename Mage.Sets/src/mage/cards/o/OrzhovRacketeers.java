package mage.cards.o;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.keyword.AfterlifeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OrzhovRacketeers extends CardImpl {

    public OrzhovRacketeers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever Orzhov Racketeers deals combat damage to a player, that player discards a card.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new DiscardTargetEffect(1), false, true
        ));

        // Afterlife 2
        this.addAbility(new AfterlifeAbility(2));

    }

    private OrzhovRacketeers(final OrzhovRacketeers card) {
        super(card);
    }

    @Override
    public OrzhovRacketeers copy() {
        return new OrzhovRacketeers(this);
    }
}
