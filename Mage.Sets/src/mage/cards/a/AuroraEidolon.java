
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author LoneFox
 */
public final class AuroraEidolon extends CardImpl {

    public AuroraEidolon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {W}, Sacrifice Aurora Eidolon: Prevent the next 3 damage that would be dealt to any target this turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PreventDamageToTargetEffect(Duration.EndOfTurn, 3), new ManaCostsImpl<>("{W}"));
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
        // Whenever you cast a multicolored spell, you may return Aurora Eidolon from your graveyard to your hand.
        this.addAbility(new SpellCastControllerTriggeredAbility(Zone.GRAVEYARD, new ReturnSourceFromGraveyardToHandEffect(), StaticFilters.FILTER_SPELL_A_MULTICOLORED, true, false));
    }

    private AuroraEidolon(final AuroraEidolon card) {
        super(card);
    }

    @Override
    public AuroraEidolon copy() {
        return new AuroraEidolon(this);
    }
}
