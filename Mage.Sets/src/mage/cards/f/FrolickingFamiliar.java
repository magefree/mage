package mage.cards.f;

import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FrolickingFamiliar extends AdventureCard {

    public FrolickingFamiliar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.OTTER, SubType.WIZARD}, "{2}{U}",
                "Blow Off Steam",
                new CardType[]{CardType.INSTANT}, "{R}");

        // Frolicking Familiar
        this.getLeftHalfCard().setPT(2, 2);

        // Flying
        this.getLeftHalfCard().addAbility(FlyingAbility.getInstance());

        // Whenever you cast an instant or sorcery spell, Frolicking Familiar gets +1/+1 until end of turn.
        this.getLeftHalfCard().addAbility(new SpellCastControllerTriggeredAbility(
                new BoostSourceEffect(1, 1, Duration.EndOfTurn),
                StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY, false
        ));

        // Blow Off Steam
        // Blow Off Steam deals 1 damage to any target.
        this.getRightHalfCard().getSpellAbility().addEffect(new DamageTargetEffect(1));
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetAnyTarget());

        finalizeCard();
    }

    private FrolickingFamiliar(final FrolickingFamiliar card) {
        super(card);
    }

    @Override
    public FrolickingFamiliar copy() {
        return new FrolickingFamiliar(this);
    }
}
