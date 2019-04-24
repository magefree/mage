
package mage.cards.d;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.dynamicvalue.common.TargetConvertedManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.permanent.token.SaprolingToken;
import mage.target.TargetPermanent;

/**
 *
 * @author North
 */
public final class DeathMutation extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nonblack creature");

    static {
        filter.add(Predicates.not(new ColorPredicate(ObjectColor.BLACK)));
    }

    public DeathMutation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{6}{B}{G}");


        // Destroy target nonblack creature. It can't be regenerated.
        this.getSpellAbility().addEffect(new DestroyTargetEffect(true));
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
        // create X 1/1 green Saproling creature tokens, where X is that creature's converted mana cost.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new SaprolingToken(), new TargetConvertedManaCost()));
    }

    public DeathMutation(final DeathMutation card) {
        super(card);
    }

    @Override
    public DeathMutation copy() {
        return new DeathMutation(this);
    }
}
