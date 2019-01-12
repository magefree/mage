package mage.cards.w;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.SpellAbilityType;
import mage.game.permanent.token.WardenSphinxToken;
import mage.target.common.TargetAttackingOrBlockingCreature;

import java.util.UUID;

/**
 * @author JayDi85
 */
public final class WarrantWarden extends SplitCard {

    public WarrantWarden(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, new CardType[]{CardType.SORCERY}, "{W/U}{W/U}", "{3}{W}{U}", SpellAbilityType.SPLIT);

        // Warrant
        // Put target attacking or blocking creature on top of its owner’s library.
        this.getLeftHalfCard().getSpellAbility().addEffect(new PutOnLibraryTargetEffect(true));
        this.getLeftHalfCard().getSpellAbility().addTarget(new TargetAttackingOrBlockingCreature());

        // Warden
        // Create a 4/4 white and blue Sphinx creature token with flying and vigilance.
        this.getRightHalfCard().getSpellAbility().addEffect(new CreateTokenEffect(new WardenSphinxToken()));
    }

    private WarrantWarden(final WarrantWarden card) {
        super(card);
    }

    @Override
    public WarrantWarden copy() {
        return new WarrantWarden(this);
    }
}