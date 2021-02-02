
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterSpell;

/**
 *
 * @author TheElk801
 */
public final class LightningRigCrew extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a Pirate spell");

    static {
        filter.add(SubType.PIRATE.getPredicate());
    }

    public LightningRigCrew(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(0);
        this.toughness = new MageInt(5);

        // {T}: Lightning-Rig Crew deals 1 damage to each opponent.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamagePlayersEffect(1, TargetController.OPPONENT), new TapSourceCost());
        this.addAbility(ability);

        // Whenever you cast a Pirate spell, untap Lightning-Rig Crew.
        this.addAbility(new SpellCastControllerTriggeredAbility(new UntapSourceEffect(), filter, false));
    }

    private LightningRigCrew(final LightningRigCrew card) {
        super(card);
    }

    @Override
    public LightningRigCrew copy() {
        return new LightningRigCrew(this);
    }
}
