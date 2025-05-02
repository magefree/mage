package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PactdollTerror extends CardImpl {

    public PactdollTerror(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.TOY);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Whenever this creature or another artifact you control enters, each opponent loses 1 life and you gain 1 life.
        Ability ability = new EntersBattlefieldThisOrAnotherTriggeredAbility(
                new LoseLifeOpponentsEffect(1),
                StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT,
                false, false
        );
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
        this.addAbility(ability);
    }

    private PactdollTerror(final PactdollTerror card) {
        super(card);
    }

    @Override
    public PactdollTerror copy() {
        return new PactdollTerror(this);
    }
}
