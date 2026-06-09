package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacedCard;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class MonicaRambeau extends ModalDoubleFacedCard {

    public MonicaRambeau(UUID ownerId, CardSetInfo setInfo) {
        super(
            ownerId, setInfo,
            // Monica Rambeau
            new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.HERO}, "{2}{W}",
            "Photon, Living Light",
            new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.ELEMENTAL, SubType.HERO}, "{2}{R}{W}{W}"
        );

        // 1.
        // Monica Rambeau
        // Legendary Creature — Human Hero
        this.getLeftHalfCard().setPT(new MageInt(3), new MageInt(3));

        // Flying
        this.getLeftHalfCard().addAbility(FlyingAbility.getInstance());

        // Prowess
        this.getLeftHalfCard().addAbility(new ProwessAbility());

        // {2}{R}{W}{W}: Transform Monica Rambeau. Activate only as a sorcery.
        this.getLeftHalfCard().addAbility(new ActivateAsSorceryActivatedAbility(new TransformSourceEffect(), new ManaCostsImpl<>("{2}{R}{W}{W}")));

        // 2.
        // Photon, Living Light
        // Legendary Creature — Elemental Hero
        this.getRightHalfCard().setPT(new MageInt(4), new MageInt(4));

        // Flying
        this.getRightHalfCard().addAbility(FlyingAbility.getInstance());

        // Hexproof
        this.getRightHalfCard().addAbility(HexproofAbility.getInstance());

        // Prowess
        this.getRightHalfCard().addAbility(new ProwessAbility());

        // Whenever you cast a noncreature spell, put a +1/+1 counter on each other creature you control.
        this.getRightHalfCard().addAbility(new SpellCastControllerTriggeredAbility(
            new AddCountersAllEffect(CounterType.P1P1.createInstance(), StaticFilters.FILTER_OTHER_CONTROLLED_CREATURE),
            StaticFilters.FILTER_SPELL_A_NON_CREATURE, false
        ));
    }

    private MonicaRambeau(final MonicaRambeau card) {
        super(card);
    }

    @Override
    public MonicaRambeau copy() {
        return new MonicaRambeau(this);
    }
}
