package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArgothianSprite extends CardImpl {

    public ArgothianSprite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.FAERIE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Argothian Sprite can't be blocked by artifact creatures.
        this.addAbility(new SimpleStaticAbility(new CantBeBlockedByCreaturesSourceEffect(
                StaticFilters.FILTER_PERMANENTS_ARTIFACT_CREATURE, Duration.WhileOnBattlefield
        )));

        // {7}: Put two +1/+1 counters on Argothian Sprite.
        this.addAbility(new SimpleActivatedAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)), new GenericManaCost(7)
        ));
    }

    private ArgothianSprite(final ArgothianSprite card) {
        super(card);
    }

    @Override
    public ArgothianSprite copy() {
        return new ArgothianSprite(this);
    }
}
