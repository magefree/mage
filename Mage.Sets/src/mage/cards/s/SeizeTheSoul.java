package mage.cards.s;

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
import mage.game.permanent.token.SpiritWhiteToken;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
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
        this.getSpellAbility().addEffect(new CreateTokenEffect(new SpiritWhiteToken()));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));

        // Haunt
        // When the creature Seize the Soul haunts dies, destroy target nonwhite, nonblack creature. Put a 1/1 white Spirit creature token with flying onto the battlefield.
        Ability ability = new HauntAbility(this, new DestroyTargetEffect());
        ability.addEffect(new CreateTokenEffect(new SpiritWhiteToken()));
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    private SeizeTheSoul(final SeizeTheSoul card) {
        super(card);
    }

    @Override
    public SeizeTheSoul copy() {
        return new SeizeTheSoul(this);
    }
}
