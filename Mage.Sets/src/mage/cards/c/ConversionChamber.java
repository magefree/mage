package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterArtifactCard;
import mage.game.permanent.token.PhyrexianGolemToken;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author Loki
 */
public final class ConversionChamber extends CardImpl {

    private static final FilterArtifactCard filter = new FilterArtifactCard("artifact card from a graveyard");

    public ConversionChamber(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");
        // {2}, {T}: Exile target artifact card from a graveyard. Put a charge counter on Conversion Chamber.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ExileTargetEffect(), new GenericManaCost(2));
        ability.addEffect(new AddCountersSourceEffect(CounterType.CHARGE.createInstance()));
        ability.addTarget(new TargetCardInGraveyard(filter));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
        // {2}, {T}, Remove a charge counter from Conversion Chamber: Create a 3/3 colorless Golem artifact creature token.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new PhyrexianGolemToken()), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addCost(new RemoveCountersSourceCost(CounterType.CHARGE.createInstance()));
        this.addAbility(ability);

    }

    private ConversionChamber(final ConversionChamber card) {
        super(card);
    }

    @Override
    public ConversionChamber copy() {
        return new ConversionChamber(this);
    }

}
