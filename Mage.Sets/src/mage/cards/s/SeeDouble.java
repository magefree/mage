package mage.cards.s;

import mage.abilities.Mode;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CardsInOpponentGraveyardCondition;
import mage.abilities.effects.common.CantBeCopiedSourceEffect;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.target.TargetSpell;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SeeDouble extends CardImpl {

    public SeeDouble(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}{U}");

        // This spell can't be copied.
        this.addAbility(new SimpleStaticAbility(Zone.STACK, new CantBeCopiedSourceEffect()).setRuleAtTheTop(true));

        // Choose one. If an opponent has eight or more cards in their graveyard, you may choose both.
        this.getSpellAbility().getModes().setChooseText(
                "Choose one. If an opponent has eight or more cards in their graveyard, you may choose both."
        );
        this.getSpellAbility().getModes().setMoreCondition(CardsInOpponentGraveyardCondition.EIGHT);
        this.getSpellAbility().addHint(CardsInOpponentGraveyardCondition.EIGHT.getHint());

        // * Copy target spell. You may choose new targets for the copy.
        this.getSpellAbility().addEffect(new CopyTargetSpellEffect());
        this.getSpellAbility().addTarget(new TargetSpell());

        // * Create a token that's a copy of target creature.
        this.getSpellAbility().addMode(new Mode(new CreateTokenCopyTargetEffect())
                .addTarget(new TargetCreaturePermanent()));
    }

    private SeeDouble(final SeeDouble card) {
        super(card);
    }

    @Override
    public SeeDouble copy() {
        return new SeeDouble(this);
    }
}
