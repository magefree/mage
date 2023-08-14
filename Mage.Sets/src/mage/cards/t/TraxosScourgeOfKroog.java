
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DontUntapInControllersUntapStepSourceEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterHistoricSpell;

/**
 *
 * @author TheElk801
 */
public final class TraxosScourgeOfKroog extends CardImpl {

    public TraxosScourgeOfKroog(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Traxos, Scourge of Kroog enters the battlefield tapped and doesn't untap during your untap step.
        Ability ability = new EntersBattlefieldTappedAbility(
                "{this} enters the battlefield tapped and doesn't untap during your untap step.");
        ability.addEffect(new DontUntapInControllersUntapStepSourceEffect());
        this.addAbility(ability);
        // Whenever you cast a historic spell untap Traxos.
        this.addAbility(new SpellCastControllerTriggeredAbility(new UntapSourceEffect(), new FilterHistoricSpell(), false));
    }

    private TraxosScourgeOfKroog(final TraxosScourgeOfKroog card) {
        super(card);
    }

    @Override
    public TraxosScourgeOfKroog copy() {
        return new TraxosScourgeOfKroog(this);
    }
}
