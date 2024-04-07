package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CommittedCrimeTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RavenOfFellOmens extends CardImpl {

    public RavenOfFellOmens(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you commit a crime, each opponent loses 1 life and you gain 1 life. This ability triggers only once each turn.
        Ability ability = new CommittedCrimeTriggeredAbility(new LoseLifeOpponentsEffect(1)).setTriggersOnceEachTurn(true);
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
        this.addAbility(ability);
    }

    private RavenOfFellOmens(final RavenOfFellOmens card) {
        super(card);
    }

    @Override
    public RavenOfFellOmens copy() {
        return new RavenOfFellOmens(this);
    }
}
