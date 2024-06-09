package mage.cards.k;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.hint.ValueHint;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author JayDi85
 */
public final class KabiraTakedown extends ModalDoubleFacedCard {

    private static final DynamicValue xValue
            = new PermanentsOnBattlefieldCount(StaticFilters.FILTER_CONTROLLED_CREATURES);

    public KabiraTakedown(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.INSTANT}, new SubType[]{}, "{1}{W}",
                "Kabira Plateau", new CardType[]{CardType.LAND}, new SubType[]{}, ""
        );

        // 1.
        // Kabira Takedown
        // Instant

        // Kabira Takedown deals damage equal to the number of creatures you control to target creature or planeswalker.
        this.getLeftHalfCard().getSpellAbility().addEffect(new DamageTargetEffect(xValue)
                .setText("{this} deals damage equal to the number of creatures you control to target creature or planeswalker"));
        this.getLeftHalfCard().getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
        this.getLeftHalfCard().getSpellAbility().addHint(new ValueHint("Creatures you control", xValue));

        // 2.
        // Kabira Plateau
        // Land

        // Kabira Plateau enters the battlefield tapped.
        this.getRightHalfCard().addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {W}.
        this.getRightHalfCard().addAbility(new WhiteManaAbility());
    }

    private KabiraTakedown(final KabiraTakedown card) {
        super(card);
    }

    @Override
    public KabiraTakedown copy() {
        return new KabiraTakedown(this);
    }
}
