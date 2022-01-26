package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.ExchangeControlTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EyesEverywhere extends CardImpl {

    public EyesEverywhere(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        // At the beginning of your upkeep, scry 1.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                Zone.BATTLEFIELD, new ScryEffect(1, false),
                TargetController.YOU, false
        ));

        // {5}{U}: Exchange control of Eyes Everywhere and target nonland permanent. Activate this ability only any time you could cast a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                Zone.BATTLEFIELD,
                new ExchangeControlTargetEffect(
                        Duration.EndOfGame, "Exchange control of {this} " +
                        "and target nonland permanent", true
                ), new ManaCostsImpl("{5}{U}")
        );
        ability.addTarget(new TargetNonlandPermanent());
        this.addAbility(ability);
    }

    private EyesEverywhere(final EyesEverywhere card) {
        super(card);
    }

    @Override
    public EyesEverywhere copy() {
        return new EyesEverywhere(this);
    }
}
