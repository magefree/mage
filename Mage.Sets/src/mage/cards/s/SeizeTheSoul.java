
package mage.cards.s;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.HauntAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.permanent.token.SpiritToken;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class SeizeTheSoul extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nonwhite, nonblack creature");

    static {
        filter.add(Predicates.not(new ColorPredicate(ObjectColor.BLACK)));
        filter.add(Predicates.not(new ColorPredicate(ObjectColor.WHITE)));
    }

    public SeizeTheSoul(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}{B}");

        // Destroy target nonwhite, nonblack creature. Put a 1/1 white Spirit creature token with flying onto the battlefield.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addEffect(new CreateTokenEffect(new SpiritToken()));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Haunt
        // When the creature Seize the Soul haunts dies, destroy target nonwhite, nonblack creature. Put a 1/1 white Spirit creature token with flying onto the battlefield.
        Ability ability = new HauntAbility(this, new DestroyTargetEffect());
        ability.addEffect(new CreateTokenEffect(new SpiritToken()));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    public SeizeTheSoul(final SeizeTheSoul card) {
        super(card);
    }

    @Override
    public SeizeTheSoul copy() {
        return new SeizeTheSoul(this);
    }
}
