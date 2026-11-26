package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DrawNthCardTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.target.common.TargetOpponent;

/**
 * @author muz
 */
public final class PrincessIrulanCorrino extends CardImpl {

    public PrincessIrulanCorrino(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever you draw your second card each turn, you gain 2 life.
        this.addAbility(new DrawNthCardTriggeredAbility(new GainLifeEffect(2), false, 2));

        // Whenever an opponent draws their second card each turn, they lose 2 life.
        this.addAbility(new DrawNthCardTriggeredAbility(
            new LoseLifeTargetEffect(2).setText("they lose 2 life"),
            false, TargetController.OPPONENT, 2, true
        ));

        // {T}: You and target opponent each draw two cards.
        Ability ability = new SimpleActivatedAbility(new DrawCardSourceControllerEffect(2).setText("you"), new TapSourceCost());
        ability.addEffect(new DrawCardTargetEffect(2).setText("and target opponent each draw two cards"));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private PrincessIrulanCorrino(final PrincessIrulanCorrino card) {
        super(card);
    }

    @Override
    public PrincessIrulanCorrino copy() {
        return new PrincessIrulanCorrino(this);
    }
}
