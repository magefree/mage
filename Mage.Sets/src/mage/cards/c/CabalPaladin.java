
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterHistoricSpell;

/**
 *
 * @author TheElk801
 */
public final class CabalPaladin extends CardImpl {

    public CabalPaladin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Whenever you cast a historic spell, Cabal Paladin deals 2 damage to each opponent.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new DamagePlayersEffect(Outcome.Damage, StaticValue.get(2), TargetController.OPPONENT)
                        .setText("{this} deals 2 damage to each opponent. <i>(Artifacts, legendaries, and Sagas are historic.)</i>"),
                new FilterHistoricSpell(), false
        ));
    }

    private CabalPaladin(final CabalPaladin card) {
        super(card);
    }

    @Override
    public CabalPaladin copy() {
        return new CabalPaladin(this);
    }
}
