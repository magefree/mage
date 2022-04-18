
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileCardsFromTopOfLibraryTargetEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class RavenGuildMaster extends CardImpl {

    public RavenGuildMaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.subtype.add(SubType.MUTANT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Raven Guild Master deals combat damage to a player, that player exiles the top ten cards of their library.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new ExileCardsFromTopOfLibraryTargetEffect(10, "that player"), false, true));

        // Morph {2}{U}{U}
        this.addAbility(new MorphAbility(new ManaCostsImpl("{2}{U}{U}")));
    }

    private RavenGuildMaster(final RavenGuildMaster card) {
        super(card);
    }

    @Override
    public RavenGuildMaster copy() {
        return new RavenGuildMaster(this);
    }
}
