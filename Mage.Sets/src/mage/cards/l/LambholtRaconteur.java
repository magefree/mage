package mage.cards.l;

import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.keyword.DayboundAbility;
import mage.abilities.keyword.NightboundAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LambholtRaconteur extends TransformingDoubleFacedCard {

    public LambholtRaconteur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.WEREWOLF}, "{3}{R}",
                "Lambholt Ravager",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.WEREWOLF}, "R"
        );

        // Lambholt Raconteur
        this.getLeftHalfCard().setPT(2, 4);

        // Whenever you cast a noncreature spell, Lambholt Raconteur deals 1 damage to each opponent.
        this.getLeftHalfCard().addAbility(new SpellCastControllerTriggeredAbility(
                new DamagePlayersEffect(1, TargetController.OPPONENT),
                StaticFilters.FILTER_SPELL_A_NON_CREATURE, false
        ));

        // Daybound
        this.getLeftHalfCard().addAbility(new DayboundAbility());

        // Lambholt Ravager
        this.getRightHalfCard().setPT(4, 4);

        // Whenever you cast a noncreature spell, Lambholt Ravager deals 2 damage to each opponent.
        this.getRightHalfCard().addAbility(new SpellCastControllerTriggeredAbility(
                new DamagePlayersEffect(2, TargetController.OPPONENT),
                StaticFilters.FILTER_SPELL_A_NON_CREATURE, false
        ));

        // Nightbound
        this.getRightHalfCard().addAbility(new NightboundAbility());
    }

    private LambholtRaconteur(final LambholtRaconteur card) {
        super(card);
    }

    @Override
    public LambholtRaconteur copy() {
        return new LambholtRaconteur(this);
    }
}
