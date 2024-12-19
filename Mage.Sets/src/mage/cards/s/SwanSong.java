package mage.cards.s;

import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.CreateTokenControllerTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.SwanSongBirdToken;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SwanSong extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("enchantment, instant, or sorcery spell");

    static {
        filter.add(Predicates.or(
                CardType.ENCHANTMENT.getPredicate(),
                CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate()
        ));
    }

    public SwanSong(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        // Counter target enchantment, instant or sorcery spell. Its controller creates a 2/2 blue Bird creature token with flying.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addEffect(new CreateTokenControllerTargetEffect(new SwanSongBirdToken()));
        this.getSpellAbility().addTarget(new TargetSpell(filter));
    }

    private SwanSong(final SwanSong card) {
        super(card);
    }

    @Override
    public SwanSong copy() {
        return new SwanSong(this);
    }
}
