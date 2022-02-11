package mage.cards.h;

import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.TreasureToken;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class Hornswoggle extends CardImpl {

    public Hornswoggle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Counter target creature spell. You create a colorless Treasure artifact token with "{T}, Sacrifice this artifact: Add one mana of any color."
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell(StaticFilters.FILTER_SPELL_CREATURE));
        this.getSpellAbility().addEffect(new CreateTokenEffect(new TreasureToken()).concatBy("You"));
    }

    private Hornswoggle(final Hornswoggle card) {
        super(card);
    }

    @Override
    public Hornswoggle copy() {
        return new Hornswoggle(this);
    }
}
