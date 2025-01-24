package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.MaxSpeedGainAbilityEffect;
import mage.abilities.keyword.StartYourEnginesAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AvishkarRaceway extends CardImpl {

    public AvishkarRaceway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Start your engines!
        this.addAbility(new StartYourEnginesAbility());

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // Max speed -- {3}, {T}, Discard a card: Draw a card.
        Ability ability = new SimpleActivatedAbility(new DrawCardSourceControllerEffect(1), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        ability.addCost(new DiscardCardCost());
        this.addAbility(new SimpleStaticAbility(new MaxSpeedGainAbilityEffect(ability)));
    }

    private AvishkarRaceway(final AvishkarRaceway card) {
        super(card);
    }

    @Override
    public AvishkarRaceway copy() {
        return new AvishkarRaceway(this);
    }
}
