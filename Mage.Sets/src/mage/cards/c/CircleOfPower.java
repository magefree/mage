package mage.cards.c;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.permanent.token.BlackWizardToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CircleOfPower extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.WIZARD, "");
    private static final FilterPermanent filter2 = new FilterPermanent(SubType.WIZARD, "");

    public CircleOfPower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}");

        // You draw two cards and you lose 2 life. Create a 0/1 black Wizard creature token with "Whenever you cast a noncreature spell, this token deals 1 damage to each opponent."
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2, true));
        this.getSpellAbility().addEffect(new LoseLifeSourceControllerEffect(2).concatBy("and"));
        this.getSpellAbility().addEffect(new CreateTokenEffect(new BlackWizardToken()));

        // Wizards you control get +1/+0 and gain lifelink until end of turn.
        this.getSpellAbility().addEffect(new BoostControlledEffect(
                1, 0, Duration.EndOfTurn
        ).setText("Wizards you control get +1/+0").concatBy("<br>"));
        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(
                LifelinkAbility.getInstance(), Duration.EndOfTurn, filter2
        ).setText("and gain lifelink until end of turn"));
    }

    private CircleOfPower(final CircleOfPower card) {
        super(card);
    }

    @Override
    public CircleOfPower copy() {
        return new CircleOfPower(this);
    }
}
