package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.keyword.DisguiseAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AlleyAssailant extends CardImpl {

    public AlleyAssailant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Alley Assailant enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // Disguise {4}{B}{B}
        this.addAbility(new DisguiseAbility(this, new ManaCostsImpl<>("{4}{B}{B}")));

        // When Alley Assailant is turned face up, target opponent loses 3 life and you gain 3 life.
        Ability ability = new TurnedFaceUpSourceTriggeredAbility(new LoseLifeTargetEffect(3));
        ability.addEffect(new GainLifeEffect(3).concatBy("and"));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private AlleyAssailant(final AlleyAssailant card) {
        super(card);
    }

    @Override
    public AlleyAssailant copy() {
        return new AlleyAssailant(this);
    }
}
