package mage.cards.o;

import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OzaisCruelty extends CardImpl {

    public OzaisCruelty(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        this.subtype.add(SubType.LESSON);

        // Ozai's Cruelty deals 2 damage to target player. That player discards two cards.
        this.getSpellAbility().addEffect(new DamageTargetEffect(2));
        this.getSpellAbility().addEffect(new DiscardTargetEffect(2).setText("That player discards two cards"));
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private OzaisCruelty(final OzaisCruelty card) {
        super(card);
    }

    @Override
    public OzaisCruelty copy() {
        return new OzaisCruelty(this);
    }
}
