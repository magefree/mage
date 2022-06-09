
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author LoneFox
 */
public final class VerdantEidolon extends CardImpl {

    public VerdantEidolon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {G}, Sacrifice Verdant Eidolon: Add three mana of any one color.
        Ability ability = new SimpleManaAbility(Zone.BATTLEFIELD, new AddManaOfAnyColorEffect(3), new ManaCostsImpl<>("{G}"));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
        // Whenever you cast a multicolored spell, you may return Verdant Eidolon from your graveyard to your hand.
        this.addAbility(new SpellCastControllerTriggeredAbility(Zone.GRAVEYARD,
                new ReturnSourceFromGraveyardToHandEffect(), StaticFilters.FILTER_SPELL_A_MULTICOLORED, true, false));
    }

    private VerdantEidolon(final VerdantEidolon card) {
        super(card);
    }

    @Override
    public VerdantEidolon copy() {
        return new VerdantEidolon(this);
    }
}
