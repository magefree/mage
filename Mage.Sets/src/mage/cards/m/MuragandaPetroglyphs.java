package mage.cards.m;

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

import java.util.Objects;
import java.util.UUID;

/**
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

    // Muraganda Petroglyphs gives a bonus only to creatures that have no rules text at all. This includes true vanilla
    // creatures (such as Grizzly Bears), face-down creatures, many tokens, and creatures that have lost their abilities
    // (due to Ovinize, for example). Any ability of any kind, whether or not the ability functions in the on the
    // battlefield zone, including things like “Cycling {2}” means the creature doesn’t get the bonus.
    // (2007-05-01)

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
            // Some Auras and Equipment grant abilities to creatures, meaning the affected creature would no longer
            // get the +2/+2 bonus. For example, Flight grants flying to the enchanted creature. Other Auras and
            // Equipment do not, meaning the affected creature would continue to get the +2/+2 bonus. For example,
            // Dehydration states something now true about the enchanted creature, but doesn’t give it any abilities.
            // Auras and Equipment that grant abilities will use the words “gains” or “has,” and they’ll list a keyword
            // ability or an ability in quotation marks.
            // (2007-05-01)

            for (Ability ability : abilities) {
                if (ability.getWorksFaceDown()) {
                    // inner face down abilities like turn up and becomes creature
                    continue;
                }
                if (!Objects.equals(ability.getClass(), SpellAbility.class) && !ability.getClass().equals(JohanVigilanceAbility.class)) {
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
