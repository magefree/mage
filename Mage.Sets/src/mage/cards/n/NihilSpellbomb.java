package mage.cards.n;

import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ExileGraveyardAllTargetPlayerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author North
 */
public final class NihilSpellbomb extends CardImpl {

    public NihilSpellbomb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // {T}, Sacrifice Nihil Spellbomb: Exile all cards from target player's graveyard.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(
                new ExileGraveyardAllTargetPlayerEffect(), new TapSourceCost()
        );
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // When Nihil Spellbomb is put into a graveyard from the battlefield, you may pay {B}. If you do, draw a card.
        this.addAbility(new DiesSourceTriggeredAbility(new DoIfCostPaid(
                new DrawCardSourceControllerEffect(1), new ManaCostsImpl<>("{B}")
        ), false).setTriggerPhrase("When {this} is put into a graveyard from the battlefield, "));
    }

    private NihilSpellbomb(final NihilSpellbomb card) {
        super(card);
    }

    @Override
    public NihilSpellbomb copy() {
        return new NihilSpellbomb(this);
    }
}
