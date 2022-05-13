
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.LoseHalfLifeEffect;
import mage.abilities.effects.common.LoseHalfLifeTargetEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author BijanT
 */
public final class EbonbladeReaper extends CardImpl {

    public EbonbladeReaper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        //Whenever Ebonblade Reaper attacks, you lose half your life, rounded up.
        this.addAbility(new AttacksTriggeredAbility(new LoseHalfLifeEffect(), false));

        //Whenever Ebonblade Reaper deals combat damage to a player, that player loses half their life, rounded up.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new LoseHalfLifeTargetEffect(), false, true));

        //Morph {3}{B}{B}
        this.addAbility(new MorphAbility(new ManaCostsImpl<>("{3}{B}{B}")));
    }

    private EbonbladeReaper(final EbonbladeReaper card) {
        super(card);
    }

    @Override
    public Card copy() {
        return new EbonbladeReaper(this);
    }
}
