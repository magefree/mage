package mage.cards.a;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SourceDealsNoncombatDamageToOpponentTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.watchers.common.DamagedPlayerThisCombatWatcher;

/**
 *
 * @author muz
 */
public final class AsgardianInspiration extends CardImpl {

    public AsgardianInspiration(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}");

        // Exile the top card of your library. You may play it this turn.
        this.getSpellAbility().addEffect(
            new ExileTopXMayPlayUntilEffect(1, Duration.EndOfTurn).withTextOptions("it", true)
        );

        // Whenever a source you control deals noncombat damage to an opponent, you may pay {2}. If you do, return this card from your graveyard to your hand.
        Ability ability = new SourceDealsNoncombatDamageToOpponentTriggeredAbility(
            Zone.GRAVEYARD,
            new DoIfCostPaid(
                new ReturnSourceFromGraveyardToHandEffect(),
                new ManaCostsImpl<>("{2}")
            ),
            false, SetTargetPointer.NONE
        );
        ability.addWatcher(new DamagedPlayerThisCombatWatcher());
        this.addAbility(ability);
    }

    private AsgardianInspiration(final AsgardianInspiration card) {
        super(card);
    }

    @Override
    public AsgardianInspiration copy() {
        return new AsgardianInspiration(this);
    }
}
