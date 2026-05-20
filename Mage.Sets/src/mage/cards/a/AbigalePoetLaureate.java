package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.BecomePreparedSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardSetInfo;
import mage.cards.PrepareCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AbigalePoetLaureate extends PrepareCard {

    public AbigalePoetLaureate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{B}", "Heroic Stanza", new CardType[]{CardType.SORCERY}, "{1}{W/B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.BARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you cast a creature spell, Abigale becomes prepared.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new BecomePreparedSourceEffect(), StaticFilters.FILTER_SPELL_A_CREATURE, false
        ));

        // Heroic Stanza
        // Sorcery {1}{W/B}
        // Put a +1/+1 counter on target creature.
        this.getSpellCard().getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        this.getSpellCard().getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private AbigalePoetLaureate(final AbigalePoetLaureate card) {
        super(card);
    }

    @Override
    public AbigalePoetLaureate copy() {
        return new AbigalePoetLaureate(this);
    }
}
