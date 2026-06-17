package mage.cards.s;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldWithCounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.VibraniumToken;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author muz
 */
public final class ShurisFabricator extends CardImpl {

    public ShurisFabricator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // When this artifact enters, create two tapped Vibranium tokens.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new VibraniumToken(), 2, true)));

        // {6}, {T}: Return target artifact card from your graveyard to the battlefield with a finality counter on it. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
            new ReturnFromGraveyardToBattlefieldWithCounterTargetEffect(CounterType.FINALITY.createInstance()),
            new ManaCostsImpl<>("{6}")
        );
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_ARTIFACT));
        this.addAbility(ability);
    }

    private ShurisFabricator(final ShurisFabricator card) {
        super(card);
    }

    @Override
    public ShurisFabricator copy() {
        return new ShurisFabricator(this);
    }
}
