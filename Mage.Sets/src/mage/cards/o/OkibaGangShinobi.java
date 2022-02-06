
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.keyword.NinjutsuAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class OkibaGangShinobi extends CardImpl {

    public OkibaGangShinobi(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{B}");
        this.subtype.add(SubType.RAT);
        this.subtype.add(SubType.NINJA);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Ninjutsu {3}{B} ({3}{B}, Return an unblocked attacker you control to hand: Put this card onto the battlefield from your hand tapped and attacking.)
        this.addAbility(new NinjutsuAbility("{3}{B}"));

        // Whenever Okiba-Gang Shinobi deals combat damage to a player, that player discards two cards.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new DiscardTargetEffect(2), false, true));
    }

    private OkibaGangShinobi(final OkibaGangShinobi card) {
        super(card);
    }

    @Override
    public OkibaGangShinobi copy() {
        return new OkibaGangShinobi(this);
    }
}