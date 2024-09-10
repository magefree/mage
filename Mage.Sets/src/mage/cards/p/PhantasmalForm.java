package mage.cards.p;

import mage.MageInt;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.game.permanent.token.TokenImpl;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PhantasmalForm extends CardImpl {

    public PhantasmalForm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Until end of turn, up to two target creatures each have base power and toughness 3/3, gain flying, and become blue Illusions in addition to their other colors and types.
        this.getSpellAbility().addEffect(new BecomesCreatureTargetEffect(
                        new PhantasmalFormToken(), false, false, Duration.EndOfTurn
                ).withDurationRuleAtStart(true).setText("Until end of turn, up to two target creatures each have base power and toughness 3/3, " +
                        "gain flying, and become blue Illusions in addition to their other colors and types.")
        );
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 2));

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private PhantasmalForm(final PhantasmalForm card) {
        super(card);
    }

    @Override
    public PhantasmalForm copy() {
        return new PhantasmalForm(this);
    }
}

class PhantasmalFormToken extends TokenImpl {

    PhantasmalFormToken() {
        super("", "");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.ILLUSION);
        color.setBlue(true);
        power = new MageInt(3);
        toughness = new MageInt(3);

        addAbility(FlyingAbility.getInstance());
    }

    private PhantasmalFormToken(final PhantasmalFormToken token) {
        super(token);
    }

    public PhantasmalFormToken copy() {
        return new PhantasmalFormToken(this);
    }
}
