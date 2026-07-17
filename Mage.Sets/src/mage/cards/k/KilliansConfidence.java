package mage.cards.k;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.OneOrMoreCombatDamagePlayerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.DamagedPlayerThisCombatWatcher;

/**
 *
 * @author muz
 */
public final class KilliansConfidence extends CardImpl {

    public KilliansConfidence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{W}{B}");

        // Target creature gets +1/+1 until end of turn. Draw a card.
        this.getSpellAbility().addEffect(new BoostTargetEffect(1, 1));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));

        // Whenever one or more creatures you control deal combat damage to a player, you may pay {W/B}. If you do, return this card from your graveyard to your hand.
        Ability ability = new OneOrMoreCombatDamagePlayerTriggeredAbility(
            Zone.GRAVEYARD,
            new DoIfCostPaid(
                new ReturnSourceFromGraveyardToHandEffect(),
                new ManaCostsImpl<>("{W/B}")
            ),
            StaticFilters.FILTER_PERMANENT_CREATURES,
            SetTargetPointer.PLAYER,
            false
        );
        ability.addWatcher(new DamagedPlayerThisCombatWatcher());
        this.addAbility(ability);
    }

    private KilliansConfidence(final KilliansConfidence card) {
        super(card);
    }

    @Override
    public KilliansConfidence copy() {
        return new KilliansConfidence(this);
    }
}
