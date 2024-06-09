package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.UntapAllLandsControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.game.permanent.token.Pest11GainLifeToken;

import java.util.UUID;

/**
 * @author weirddan455
 */
public final class BeledrosWitherbloom extends CardImpl {

    public BeledrosWitherbloom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELDER);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of each upkeep, create a 1/1 black and green Pest creature token with "When this creature dies, you gain 1 life."
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new CreateTokenEffect(new Pest11GainLifeToken()), TargetController.EACH_PLAYER, false));

        // Pay 10 life: Untap all lands you control. Activate only once each turn.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(new UntapAllLandsControllerEffect()
                .setText("Untap all lands you control. Activate only once each turn"), new PayLifeCost(10));
        ability.setMaxActivationsPerTurn(1);
        this.addAbility(ability);
    }

    private BeledrosWitherbloom(final BeledrosWitherbloom card) {
        super(card);
    }

    @Override
    public BeledrosWitherbloom copy() {
        return new BeledrosWitherbloom(this);
    }
}
