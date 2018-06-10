
package mage.cards.m;

import java.util.Objects;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Abilities;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.special.JohanVigilanceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.game.Game;

/**
 *
 * @author anonymous
 */
public final class MuragandaPetroglyphs extends CardImpl {

    private static final FilterCreaturePermanent filterNoAbilities
            = new FilterCreaturePermanent("Creatures with no ability");

    static {
        filterNoAbilities.add(new NoAbilityPredicate());
    }

    public MuragandaPetroglyphs(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}");
        this.expansionSetCode = "FUT";

        // Creatures with no abilities get +2/+2.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(
                2, 2, Duration.WhileOnBattlefield, filterNoAbilities, false)));
    }

    public MuragandaPetroglyphs(final MuragandaPetroglyphs card) {
        super(card);
    }

    @Override
    public MuragandaPetroglyphs copy() {
        return new MuragandaPetroglyphs(this);
    }
}

class NoAbilityPredicate implements Predicate<MageObject> {

    @Override
    public boolean apply(MageObject input, Game game) {
        boolean isFaceDown = false;
        Abilities<Ability> abilities;
        if (input instanceof Card) {
            abilities = ((Card) input).getAbilities(game);
            isFaceDown = ((Card) input).isFaceDown(game);
        } else {
            abilities = input.getAbilities();
        }
        if (isFaceDown) {
            for (Ability ability : abilities) {
                if (!ability.getSourceId().equals(input.getId()) && !ability.getClass().equals(JohanVigilanceAbility.class)) {
                    return false;
                }
            }
            return true;
        }

        for (Ability ability : abilities) {
            if (!Objects.equals(ability.getClass(), SpellAbility.class) && !ability.getClass().equals(JohanVigilanceAbility.class)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "with no abilities";
    }
}
