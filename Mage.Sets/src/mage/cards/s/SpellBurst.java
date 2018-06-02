
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.keyword.BuybackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.target.TargetSpell;

/**
 * @author LevelX2
 */
public final class SpellBurst extends CardImpl {

    public SpellBurst(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{U}");


        // Buyback {3}
        this.addAbility(new BuybackAbility("{3}"));

        // Counter target spell with converted mana cost X.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell(new FilterSpell("spell with converted mana cost X")));
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof SpellAbility) {
            ability.getTargets().clear();
            FilterSpell filter = new FilterSpell("spell with converted mana cost X");
            filter.add(new ConvertedManaCostPredicate(ComparisonType.EQUAL_TO, ability.getManaCostsToPay().getX()));
            ability.addTarget(new TargetSpell(filter));
        }
    }

    public SpellBurst(final SpellBurst card) {
        super(card);
    }

    @Override
    public SpellBurst copy() {
        return new SpellBurst(this);
    }
}
