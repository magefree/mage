
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.TargetPlayer;

/**
 *
 * @author LoneFox
 */
public final class EntropicEidolon extends CardImpl {

    public EntropicEidolon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {B}, Sacrifice Entropic Eidolon: Target player loses 1 life and you gain 1 life.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new LoseLifeTargetEffect(1), new ManaCostsImpl<>("{B}"));
        Effect effect = new GainLifeEffect(1);
        effect.setText("and you gain 1 life");
        ability.addEffect(effect);
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
        // Whenever you cast a multicolored spell, you may return Entropic Eidolon from your graveyard to your hand.
        this.addAbility(new SpellCastControllerTriggeredAbility(Zone.GRAVEYARD,
                new ReturnSourceFromGraveyardToHandEffect(), StaticFilters.FILTER_SPELL_A_MULTICOLORED, true, false));
    }

    private EntropicEidolon(final EntropicEidolon card) {
        super(card);
    }

    @Override
    public EntropicEidolon copy() {
        return new EntropicEidolon(this);
    }
}
