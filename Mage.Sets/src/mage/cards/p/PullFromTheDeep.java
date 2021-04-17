
package mage.cards.p;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.SecondTargetPointer;

/**
 *
 * @author LevelX2
 */
public final class PullFromTheDeep extends CardImpl {

    private static final FilterCard filterInstant = new FilterCard("instant card from your graveyard");
    private static final FilterCard filterSorcery = new FilterCard("sorcery card from your graveyard");

    static {
        filterInstant.add(CardType.INSTANT.getPredicate());
        filterSorcery.add(CardType.SORCERY.getPredicate());
    }

    public PullFromTheDeep(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{U}{U}");


        // Return up to one target instant card and up to one target sorcery card from your graveyard to your hand. Exile Pull from the Deep.
        Effect effect = new ReturnToHandTargetEffect();
        effect.setText("Return up to one target instant card");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(0,1,filterInstant));
        effect = new ReturnToHandTargetEffect();
        effect.setText("and up to one target sorcery card from your graveyard to your hand");
        effect.setTargetPointer(new SecondTargetPointer());
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(0,1,filterSorcery));
        this.getSpellAbility().addEffect(new ExileSpellEffect());
    }

    private PullFromTheDeep(final PullFromTheDeep card) {
        super(card);
    }

    @Override
    public PullFromTheDeep copy() {
        return new PullFromTheDeep(this);
    }
}
