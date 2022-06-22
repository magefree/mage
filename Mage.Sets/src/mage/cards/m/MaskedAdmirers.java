
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author LevelX2
 */
public final class MaskedAdmirers extends CardImpl {

    public MaskedAdmirers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Masked Admirers enters the battlefield, draw a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1), false));
        // Whenever you cast a creature spell, you may pay {G}{G}. If you do, return Masked Admirers from your graveyard to your hand.
        OneShotEffect effect = new ReturnToHandSourceEffect();
        effect.setText("return {this} from your graveyard to your hand");
        this.addAbility(new SpellCastControllerTriggeredAbility(
                Zone.GRAVEYARD, new DoIfCostPaid(effect, new ManaCostsImpl<>("{G}{G}")), StaticFilters.FILTER_SPELL_A_CREATURE, false, false));
    }

    private MaskedAdmirers(final MaskedAdmirers card) {
        super(card);
    }

    @Override
    public MaskedAdmirers copy() {
        return new MaskedAdmirers(this);
    }
}
