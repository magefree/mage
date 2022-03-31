package mage.cards.c;

import mage.abilities.Mode;
import mage.abilities.dynamicvalue.common.CreaturesYouControlCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.hint.common.CreaturesYouControlHint;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.game.permanent.token.CitizenGreenWhiteToken;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CabarettiCharm extends CardImpl {

    public CabarettiCharm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}{G}{W}");

        // Choose one —
        // • Cabaretti Charm deals damage equal to the number of creatures you control to target creature or planeswalker.
        this.getSpellAbility().addEffect(new DamageTargetEffect(CreaturesYouControlCount.instance));
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
        this.getSpellAbility().addHint(CreaturesYouControlHint.instance);

        // • Creatures you control get +1/+1 and gain trample until end of turn.
        this.getSpellAbility().addMode(new Mode(
                new BoostControlledEffect(
                        1, 1, Duration.EndOfTurn,
                        StaticFilters.FILTER_PERMANENT_CREATURES
                ).setText("creatures you control get +1/+1")
        ).addEffect(new GainAbilityControlledEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_CONTROLLED_CREATURE
        ).setText("and gain trample until end of turn")));

        // • Create two 1/1 green and white Citizen creature tokens.
        this.getSpellAbility().addMode(new Mode(new CreateTokenEffect(new CitizenGreenWhiteToken(), 2)));
    }

    private CabarettiCharm(final CabarettiCharm card) {
        super(card);
    }

    @Override
    public CabarettiCharm copy() {
        return new CabarettiCharm(this);
    }
}
