package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.ChangelingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LittjaraGladeWarden extends CardImpl {

    public LittjaraGladeWarden(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Changeling
        this.addAbility(new ChangelingAbility());

        // {2}{G}, {T}, Exile a creature card from your graveyard: Put two +1/+1 counters on target creature. Activate this ability only any time you could cast a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                Zone.BATTLEFIELD,
                new AddCountersTargetEffect(CounterType.P1P1.createInstance(2)),
                new ManaCostsImpl<>("{2}{G}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(
                StaticFilters.FILTER_CARD_CREATURE_A
        )));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private LittjaraGladeWarden(final LittjaraGladeWarden card) {
        super(card);
    }

    @Override
    public LittjaraGladeWarden copy() {
        return new LittjaraGladeWarden(this);
    }
}
