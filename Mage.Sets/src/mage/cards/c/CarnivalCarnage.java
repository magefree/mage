package mage.cards.c;

import mage.abilities.effects.common.DamageTargetControllerEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.SpellAbilityType;
import mage.target.common.TargetCreatureOrPlaneswalker;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CarnivalCarnage extends SplitCard {

    public CarnivalCarnage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, new CardType[]{CardType.SORCERY}, "{B/R}", "{2}{B}{R}", SpellAbilityType.SPLIT);

        // Carnival
        // Carnival deals 1 damage to target creature or planeswalker and 1 damage to that permanent's controller.
        this.getLeftHalfCard().getSpellAbility().addEffect(new DamageTargetEffect(1));
        this.getLeftHalfCard().getSpellAbility().addEffect(new DamageTargetControllerEffect(1).setText("and 1 damage to that permanent's controller"));
        this.getLeftHalfCard().getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());

        // Carnage
        // Carnage deals 3 damage to target opponent. That player discards two cards.
        this.getRightHalfCard().getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getRightHalfCard().getSpellAbility().addEffect(
                new DiscardTargetEffect(2).setText("That player discards two cards.")
        );
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetOpponent());
    }

    private CarnivalCarnage(final CarnivalCarnage card) {
        super(card);
    }

    @Override
    public CarnivalCarnage copy() {
        return new CarnivalCarnage(this);
    }
}
