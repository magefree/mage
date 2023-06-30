package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author weirddan455
 */
public final class CruelWitness extends CardImpl {

    public CruelWitness(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");

        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you cast a noncreature spell, look at the top card of your library. You may put that card into your graveyard.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new SurveilEffect(1), StaticFilters.FILTER_SPELL_A_NON_CREATURE, false
        ));
    }

    private CruelWitness(final CruelWitness card) {
        super(card);
    }

    @Override
    public CruelWitness copy() {
        return new CruelWitness(this);
    }
}
