package mage.cards.y;

import mage.abilities.Mode;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class YourTempleIsUnderAttack extends CardImpl {

    public YourTempleIsUnderAttack(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // Choose one —
        // • Pray for Protection — Creatures you control gain indestructible until end of turn.
        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURES
        ));
        this.getSpellAbility().withFirstModeFlavorWord("Pray for Protection");

        // • Strike a Deal — You and target opponent each draw two cards.
        this.getSpellAbility().addMode(new Mode(
                new DrawCardSourceControllerEffect(2).setText("you")
        ).addEffect(new DrawCardTargetEffect(2)
                .setText("and target opponent each draw two cards")
        ).addTarget(new TargetOpponent()).withFlavorWord("Strike a Deal"));
    }

    private YourTempleIsUnderAttack(final YourTempleIsUnderAttack card) {
        super(card);
    }

    @Override
    public YourTempleIsUnderAttack copy() {
        return new YourTempleIsUnderAttack(this);
    }
}
