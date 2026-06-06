package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CastNoncreatureSpellThisTurnCondition;
import mage.abilities.decorator.ConditionalRestrictionEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SwordCoastSerpent extends AdventureCard {

    public SwordCoastSerpent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.SERPENT, SubType.DRAGON}, "{5}{U}{U}",
                "Capsizing Wave",
                new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Sword Coast Serpent
        this.getLeftHalfCard().setPT(6, 6);

        // Sword Coast Serpent can't be blocked as long as you've cast a noncreature spell this turn.
        this.getLeftHalfCard().addAbility(new SimpleStaticAbility(new ConditionalRestrictionEffect(
                new CantBeBlockedSourceEffect(), CastNoncreatureSpellThisTurnCondition.instance,
                "{this} can't be blocked as long as you've cast a noncreature spell this turn"
        )).addHint(CastNoncreatureSpellThisTurnCondition.getHint()));

        // Capsizing Wave
        // Return target creature to its owner's hand.
        this.getRightHalfCard().getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent());

        finalizeCard();
    }

    private SwordCoastSerpent(final SwordCoastSerpent card) {
        super(card);
    }

    @Override
    public SwordCoastSerpent copy() {
        return new SwordCoastSerpent(this);
    }
}
