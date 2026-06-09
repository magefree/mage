package mage.cards.a;

import mage.abilities.effects.common.DamageMultiEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetAnyTargetAmount;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AmethystDragon extends AdventureCard {

    public AmethystDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, 
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.DRAGON}, "{4}{R}{R}",
                "Explosive Crystal",
                new CardType[]{CardType.SORCERY}, "{4}{R}");

        // Amethyst Dragon
        this.getLeftHalfCard().setPT(4, 4);

        // Flying
        this.getLeftHalfCard().addAbility(FlyingAbility.getInstance());

        // Haste
        this.getLeftHalfCard().addAbility(HasteAbility.getInstance());

        // Explosive Crystal
        // Explosive Crystal deals 4 damage divided as you choose among any number of targets.
        this.getRightHalfCard().getSpellAbility().addEffect(new DamageMultiEffect());
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetAnyTargetAmount(4));

        finalizeCard();
    }

    private AmethystDragon(final AmethystDragon card) {
        super(card);
    }

    @Override
    public AmethystDragon copy() {
        return new AmethystDragon(this);
    }
}
